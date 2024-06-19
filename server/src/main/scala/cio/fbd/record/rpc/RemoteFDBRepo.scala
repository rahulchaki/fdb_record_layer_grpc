package cio.fbd.record.rpc

import cio.fbd.util.FDBUtil
import cio.fdb.record.grpc.FdbCrud.{FDBCrudCommand, FDBCrudResponse, KeysList, RecordsList}
import cio.fdb.record.grpc.FdbFilters.BooleanQuery
import com.apple.foundationdb.record.metadata.Key
import com.apple.foundationdb.record.provider.foundationdb.{FDBDatabase, FDBRecordContext, FDBRecordStore}
import com.apple.foundationdb.record.query.RecordQuery
import com.apple.foundationdb.tuple.Tuple
import com.google.protobuf.ByteString

import java.util.concurrent.CompletableFuture
import java.util.function.Function
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future
import scala.jdk.CollectionConverters._
import scala.jdk.FutureConverters.{CompletionStageOps, FutureOps}

trait RemoteFDBRepo[M[_]] {
  def execute(command: FDBCrudCommand) : M[FDBCrudResponse]
}

object RemoteFDBRepo {

  def singleTxnAsync( ctx: FDBRecordContext, db: Database): RemoteFDBRepoSingleTxnAsync ={
    new RemoteFDBRepoSingleTxnAsync( ctx, db )
  }
  def async( fdb: FDBDatabase, db: Database): RemoteFDBRepoAsync ={
    new RemoteFDBRepoAsync(  fdb, db )
  }
}

class RemoteFDBRepoAsync(
                         fdb: FDBDatabase,
                         db: Database
                       ) extends RemoteFDBRepo[Future]{

  def execute(command: FDBCrudCommand) : Future[FDBCrudResponse] = {
    val processRequests: Function[_ >: FDBRecordContext, CompletableFuture[_]] =
      (ctx: FDBRecordContext) => {
        RemoteFDBRepo.singleTxnAsync( ctx, db )
          .execute( command )
          .asJava.toCompletableFuture
      }
    fdb.runAsync(processRequests).asInstanceOf[Future[FDBCrudResponse]]
  }

}


class RemoteFDBRepoSingleTxnAsync(
                                   ctx: FDBRecordContext,
                                   db: Database
                                 ) extends RemoteFDBRepo[Future]{

  private val rsBuilder = FDBRecordStore.newBuilder()
    .setMetaDataProvider(db.metadata)
  private val recordStore: FDBRecordStore = rsBuilder.copyBuilder()
    .setContext(ctx).setKeySpacePath(db.recordsKeySpace).createOrOpen()


  override def execute(command: FDBCrudCommand): Future[FDBCrudResponse] = {
    val table = command.getTable
    val responseBuilder = FDBCrudResponse.newBuilder()
      command.getOperation match {
        case FDBCrudCommand.OPERATION.CREATE =>
          if (command.hasRecords) {
            createAll(table, command.getRecords.getRecordsList.asScala.toList)
              .map{ keysCreated =>
                responseBuilder.setKeys(
                  KeysList.newBuilder().addAllKeys(keysCreated.asJava).build()
                ).build()
              }
          }
          else
            Future.failed(
              new IllegalArgumentException(" CREATE command needs list of records as data. ")
            )

        case FDBCrudCommand.OPERATION.LOAD =>
          if (command.hasKeys) {
            loadAll(command.getKeys.getKeysList.asScala.toList)
              .map{ records =>
                responseBuilder.setRecords(
                  RecordsList.newBuilder().addAllRecords(records.asJava).build()
                ).build()
              }
          }
          else if (command.hasQuery) {
            loadAll(table, command.getQuery)
              .map{ records =>
                responseBuilder.setRecords(
                  RecordsList.newBuilder().addAllRecords(records.asJava).build()
                ).build()
              }
          }
          else
            Future.failed(
              new IllegalArgumentException(" LOAD command needs either Query or list of keys  as data. ")
            )
        case FDBCrudCommand.OPERATION.DELETE =>
          if (command.hasKeys) {
            deleteAll(command.getKeys.getKeysList.asScala.toList)
              .map{ result =>
                responseBuilder.setSuccess(result).build()
              }
          }
          else if (command.hasQuery) {
            deleteAll(table, command.getQuery)
              .map{ result =>
                responseBuilder.setSuccess(result).build()
              }
          }
          else
            Future.failed(
              new IllegalArgumentException(" DELETE command needs either Query or list of keys  as data. ")
            )
        case FDBCrudCommand.OPERATION.UNRECOGNIZED =>
          Future.failed( new IllegalArgumentException(" UNKNOWN command found  "))
      }
  }
  def createAll(table: String, records: List[ByteString]): Future[List[ByteString]] = {
    val recordType = db.metadata.getRecordType(table)
    val recordsProto = records.map( rec => MetadataManager.toMessage(rec, recordType))
    val recordsF = recordsProto.map{ proto =>
      recordStore.saveRecordAsync( proto ).asScala
        .map{ storedRecord =>
          recordStore.saveRecordAsync( proto )
          val primaryKey = storedRecord.getPrimaryKey
          ByteString.copyFrom( primaryKey.pack())
        }
    }
    Future.sequence( recordsF)
  }

  private def toTuple(value: AnyRef): Tuple = {
    value match {
      case inner: ByteString =>
        Tuple.fromBytes(inner.toByteArray)
      case inner: Iterable[_] =>
        Key.Evaluated.concatenate(inner.asJava).toTuple
      case inner: java.util.Collection[_] =>
        Key.Evaluated.concatenate(inner).toTuple
      case inner =>
        Key.Evaluated.scalar(inner).toTuple
    }
  }
  def loadAll(keys: List[AnyRef]): Future[List[ByteString]] = {
    Future.sequence(
      keys.map(toTuple).map(recordStore.loadRecordAsync).map(_.asScala)
    ).map( _.map(_.getRecord.toByteString))
  }
  def loadAll(table: String, query: BooleanQuery): Future[List[ByteString]] = {
    val rQuery = RecordQuery.newBuilder().setRecordType(table).setFilter(FDBUtil.toFDBQuery(query)).build()
    val cursor = recordStore.executeQuery(rQuery)
    cursor.asList().asScala
      .map{ recs =>
        recs.asScala.map(_.getRecord.toByteString).toList
      }
  }

  def deleteAll(keys: List[AnyRef]): Future[Boolean] = {
    Future.sequence(
      keys.map(toTuple).map(recordStore.deleteRecordAsync).map(_.asScala)
    ).map(_.forall(_==true))
  }

  def deleteAll(table: String, query: BooleanQuery): Future[Boolean] = {
    recordStore.deleteRecordsWhereAsync(table, FDBUtil.toFDBQuery(query))
      .asScala.map(_ => true)
  }
}