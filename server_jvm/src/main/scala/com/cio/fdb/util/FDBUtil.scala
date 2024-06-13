package com.cio.fdb.util

import com.apple.foundationdb.directory.DirectoryLayer
import com.apple.foundationdb.{Database, KeyValue, Range, Transaction}

import scala.jdk.CollectionConverters._

object FDBUtil {

  val ENTIRE_USER_KEY_RANGE = new Range( Array.emptyByteArray, Array(255.toByte))

  def clearAll( db: Database ): Unit = {
    db.run((tr: Transaction) => tr.clear(ENTIRE_USER_KEY_RANGE))
  }

  def enlistDirectories( db: Database, path: List[ String] = List.empty ): Unit = {
    println( path.mkString("/"))
    val directories = db.read { tr =>
      DirectoryLayer.getDefault.list( tr, path.asJava ).join()
    }
    directories.asScala.foreach { dir =>
      enlistDirectories(db, path ++ List( dir ))
    }
  }
  def fetchAll( db: Database, range: Option[Range] = None  ): Iterator[ List[KeyValue] ] = {
    val splits = db.run{ tr =>
      val splitsArray = tr.getRangeSplitPoints( range.getOrElse(ENTIRE_USER_KEY_RANGE), 1024*1024 ).join().getKeys
      val splits = splitsArray.asScala
        .grouped(2).filter( _.size == 2 )
        .map( _.toList)
        .map( split => new Range( split.head, split.last ))
      splits
    }
    splits.map{ range =>
      db.run{tr =>
        val asyncIterable  = tr.getRange( range)
        val jFuture = asyncIterable.asList()
        jFuture.join()
      }.asScala.toList
    }
  }

  def displayAll( kvPairs: List[KeyValue] ): Unit = {
    kvPairs.foreach{ kv =>
      val key = kv.getKey
      val value = kv.getValue
      println("====================================")
      println( ByteArrayReadable.formatSafe( key) )
      println( ByteArrayReadable.formatSafe( value))
      println("====================================")
    }
  }
}
