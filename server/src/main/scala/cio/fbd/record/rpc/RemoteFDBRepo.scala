package cio.fbd.record.rpc

import cio.fbd.util.FDBUtil
import cio.fdb.record.grpc.FdbCrud.{FDBCrudCommand, FDBCrudResponse, KeysList, RecordsList}
import cio.fdb.record.grpc.FdbFilters.BooleanQuery
import com.apple.foundationdb.record.metadata.Key
import com.apple.foundationdb.record.provider.foundationdb.{FDBDatabase, FDBRecordContext, FDBRecordStore}
import com.apple.foundationdb.record.query.RecordQuery
import com.apple.foundationdb.tuple.Tuple
import com.google.protobuf.ByteString

import java.util.concurrent.Executors
import scala.concurrent.{ExecutionContext, Future}
import scala.jdk.CollectionConverters._
import scala.util.{Failure, Success, Try}

trait RemoteFDBRepo[M[_]]{
  def execute(command: FDBCrudCommand) : M[FDBCrudResponse]
}

object RemoteFDBRepo {
  def singleTxnSync( ctx: FDBRecordContext, db: Database): RemoteFDBRepoSingleTxnSync = {
    new RemoteFDBRepoSingleTxnSync( ctx, db)
  }
  def sync( fdb: FDBDatabase, db: Database ): RemoteFDBRepoSync ={
    new RemoteFDBRepoSync( fdb, db )
  }

  def singleTxnAsync( ctx: FDBRecordContext, db: Database): RemoteFDBRepoAsync ={
    new RemoteFDBRepoAsync(singleTxnSync( ctx, db ))
  }
  def async( fdb: FDBDatabase, db: Database): RemoteFDBRepoAsync ={
    new RemoteFDBRepoAsync(sync( fdb, db ))
  }
}

class RemoteFDBRepoAsync( sync: RemoteFDBRepo[Try]) extends RemoteFDBRepo[Future]{
  private val ex = ExecutionContext.fromExecutor(
    Executors.newCachedThreadPool()
  )
  override def execute(command: FDBCrudCommand): Future[FDBCrudResponse] = {
    Future {
      sync.execute(command) match {
        case Failure(ex) =>
          throw ex
        case Success(response) =>
          response
      }
    }(ex)
  }
}

class RemoteFDBRepoSingleTxnSync(
                                  val ctx: FDBRecordContext,
                                  db: Database
                                ) extends RemoteFDBRepo[Try]{
  private val rsBuilder = FDBRecordStore.newBuilder()
    .setMetaDataProvider(db.metadata)
  val recordStore: FDBRecordStore = rsBuilder.copyBuilder()
    .setContext(ctx).setKeySpacePath(db.recordsKeySpace).createOrOpen()

  def execute(command: FDBCrudCommand) : Try[FDBCrudResponse] = {
    val table = command.getTable
    val responseBuilder = FDBCrudResponse.newBuilder()
    Try {
      command.getOperation match {
        case FDBCrudCommand.OPERATION.CREATE =>
          if (command.hasRecords) {
            val keysCreated =
              createAll(table, command.getRecords.getRecordsList.asScala.toList).asJava
            responseBuilder.setKeys(
              KeysList.newBuilder().addAllKeys(keysCreated).build()
            ).build()
          }
          else
            throw new IllegalArgumentException(" CREATE command needs list of records as data. ")

        case FDBCrudCommand.OPERATION.LOAD =>
          if (command.hasKeys) {
            responseBuilder.setRecords(
              RecordsList.newBuilder().addAllRecords(
                loadAll(command.getKeys.getKeysList.asScala.toList).asJava
              ).build()
            ).build()
          }
          else if (command.hasQuery) {
            val result = loadAll(table, command.getQuery)
            responseBuilder.setRecords(
              RecordsList.newBuilder().addAllRecords(result.asJava).build()
            ).build()
          }
          else
            throw new IllegalArgumentException(" LOAD command needs either Query or list of keys  as data. ")
        case FDBCrudCommand.OPERATION.DELETE =>
          if (command.hasKeys) {
            responseBuilder.setSuccess(
              deleteAll(command.getKeys.getKeysList.asScala.toList)
            ).build()
          }
          else if (command.hasQuery) {
            responseBuilder.setSuccess(
              deleteAll(table, command.getQuery)
            ).build()
          }
          else
            throw new IllegalArgumentException(" DELETE command needs either Query or list of keys  as data. ")
        case FDBCrudCommand.OPERATION.UNRECOGNIZED =>
          throw new IllegalArgumentException(" UNKNOWN command found  ")
      }
    }
  }


  def createAll(table: String, records: List[ByteString]): List[ByteString] = {
    val recordType = db.metadata.getRecordType(table)
    records.map{ rec =>
      val proto = MetadataManager.toMessage(rec, recordType)
      val storedRecord = recordStore.saveRecord( proto )
      val primaryKey = storedRecord.getPrimaryKey
      ByteString.copyFrom( primaryKey.pack())
    }
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
  def loadAll(keys: List[AnyRef]): List[ByteString] = {
    keys.map(toTuple).map(recordStore.loadRecord)
      .map(_.getRecord.toByteString)
  }

  def deleteAll(keys: List[AnyRef]): Boolean = {
    keys.map(toTuple).forall(recordStore.deleteRecord)
  }

  def loadAll(table: String, query: BooleanQuery): List[ByteString] = {
    val rQuery = RecordQuery.newBuilder().setRecordType(table).setFilter(FDBUtil.toFDBQuery(query)).build()
    val cursor = recordStore.executeQuery(rQuery)
    cursor.asList().join()
      .asScala.map(_.getRecord.toByteString).toList
  }

  def deleteAll(table: String, query: BooleanQuery): Boolean = {
    val recordStore = rsBuilder.copyBuilder().setContext(ctx).setKeySpacePath(db.recordsKeySpace).createOrOpen()
    recordStore.deleteRecordsWhere(table, FDBUtil.toFDBQuery(query))
    true
  }

}

class RemoteFDBRepoSync(
                         fdb: FDBDatabase,
                         db: Database
                       ) extends RemoteFDBRepo[Try]{

  def execute(command: FDBCrudCommand) : Try[FDBCrudResponse] = {
    Try{
      fdb.run{ ctx =>
        RemoteFDBRepo.singleTxnSync( ctx, db )
          .execute(command)
      }
    }.flatten
  }

}
