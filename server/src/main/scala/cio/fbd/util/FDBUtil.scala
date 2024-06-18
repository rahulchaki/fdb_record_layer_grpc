package cio.fbd.util

import cio.fdb.record.grpc.Filters
import cio.fdb.record.grpc.Filters.{Operator, Reducer}
import com.apple.foundationdb.directory.DirectoryLayer
import com.apple.foundationdb.record.query.expressions.{Comparisons, FieldWithComparison, Query, QueryComponent}
import com.apple.foundationdb.{Database, KeyValue, Range, Transaction}

import scala.jdk.CollectionConverters._

object FDBUtil {

  val ENTIRE_USER_KEY_RANGE = new Range(Array.emptyByteArray, Array(255.toByte))

  def clearAll(db: Database): Unit = {
    db.run((tr: Transaction) => tr.clear(ENTIRE_USER_KEY_RANGE))
  }

  def enlistDirectories(db: Database, path: List[String] = List.empty): Unit = {
    println(path.mkString("/"))
    val directories = db.read { tr =>
      DirectoryLayer.getDefault.list(tr, path.asJava).join()
    }
    directories.asScala.foreach { dir =>
      enlistDirectories(db, path ++ List(dir))
    }
  }

  def fetchAll(db: Database, range: Option[Range] = None): Iterator[List[KeyValue]] = {
    val splits = db.run { tr =>
      val splitsArray = tr.getRangeSplitPoints(range.getOrElse(ENTIRE_USER_KEY_RANGE), 1024 * 1024).join().getKeys
      val splits = splitsArray.asScala
        .grouped(2).filter(_.size == 2)
        .map(_.toList)
        .map(split => new Range(split.head, split.last))
      splits
    }
    splits.map { range =>
      db.run { tr =>
        val asyncIterable = tr.getRange(range)
        val jFuture = asyncIterable.asList()
        jFuture.join()
      }.asScala.toList
    }
  }

  def displayAll(kvPairs: List[KeyValue]): Unit = {
    kvPairs.foreach { kv =>
      val key = kv.getKey
      val value = kv.getValue
      println("====================================")
      println(ByteArrayReadable.formatSafe(key))
      println(ByteArrayReadable.formatSafe(value))
      println("====================================")
    }
  }

  def toFDBQuery(query: Filters.BooleanQuery): QueryComponent = {
    val expressions = query.getExpressionsList.asScala.map { exp =>
      val comparand =
        if (exp.getValue.hasIntValue) {
          java.lang.Long.valueOf(exp.getValue.getIntValue.intValue)
        } else if (exp.getValue.hasDoubleValue) {
          java.lang.Double.valueOf(exp.getValue.getDoubleValue)
        } else if (exp.getValue.hasStringValue) {
          exp.getValue.getStringValue
        } else if (exp.getValue.hasIntListValue) {
          exp.getValue.getIntListValue.getValueList
        } else if (exp.getValue.hasDoubleListValue) {
          exp.getValue.getDoubleListValue.getValueList
        } else if (exp.getValue.hasStringListValue) {
          exp.getValue.getStringListValue.getValueList.asByteStringList()
        }

      val comparison: Comparisons.Comparison = exp.getOperator.getOperation match {
        case Operator.OPERATION.EQ => new Comparisons.SimpleComparison(Comparisons.Type.EQUALS, comparand)
        case Operator.OPERATION.N_EQ => new Comparisons.SimpleComparison(Comparisons.Type.NOT_EQUALS, comparand)
        case Operator.OPERATION.GT => new Comparisons.SimpleComparison(Comparisons.Type.GREATER_THAN, comparand)
        case Operator.OPERATION.GTE => new Comparisons.SimpleComparison(Comparisons.Type.GREATER_THAN_OR_EQUALS, comparand)
        case Operator.OPERATION.LT => new Comparisons.SimpleComparison(Comparisons.Type.LESS_THAN, comparand)
        case Operator.OPERATION.LTE => new Comparisons.SimpleComparison(Comparisons.Type.LESS_THAN_OR_EQUALS, comparand)
        case Operator.OPERATION.IN => new Comparisons.ListComparison(Comparisons.Type.IN, comparand.asInstanceOf[java.util.List[_]])
      }

      new FieldWithComparison(exp.getField.getName, comparison).asInstanceOf[QueryComponent]
    }.toList.asJava
    if(expressions.size() == 1)
      expressions.get(0)
    else {
      query.getReducer.getRduction match {
        case Reducer.REDUCTION.AND => Query.and(expressions)
        case Reducer.REDUCTION.OR => Query.or(expressions)
      }
    }
  }

}
