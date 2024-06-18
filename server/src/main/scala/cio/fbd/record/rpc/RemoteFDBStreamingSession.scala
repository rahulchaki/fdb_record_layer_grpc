package cio.fbd.record.rpc

import cio.fdb.record.grpc.FdbCrud.{FDBCrudCommand, FDBCrudResponse}
import cio.fdb.record.grpc.FdbMetadataManager.FDBDatabaseMetadata
import cio.fdb.record.grpc.FdbSession._
import com.apple.foundationdb.record.provider.foundationdb.FDBRecordContext
import io.grpc.stub.StreamObserver

import java.util.concurrent.atomic.AtomicReference
import scala.util.{Failure, Success, Try}


trait RemoteFDBStreamingSessionHandler[M[_]] {
  def isInitialized: Boolean
  def handle( command: FDBNewStreamingSessionComand ): M[FDBDatabaseMetadata]
  def handle( command: FDBCrudCommand ): M[FDBCrudResponse]
}

class RemoteFDBStreamingSessionHandlerSync(
                                            ctx: FDBRecordContext,
                                            metadataManager: MetadataManagerSync
                                          ) extends RemoteFDBStreamingSessionHandler[Try]{

  private val repoAtm = new AtomicReference[RemoteFDBRepoSingleTxnSync]()


  override def isInitialized: Boolean = Option(repoAtm.get()).isDefined

  override def handle(command: FDBNewStreamingSessionComand): Try[FDBDatabaseMetadata] = {
    val dbName = command.getDatabase
    metadataManager.open(dbName) match {
      case Failure( ex ) =>
        Failure( ex )
      case Success(None) =>
        Failure( new IllegalStateException(s" Metadata for $dbName could not be opened ") )
      case Success(Some( db )) =>
        repoAtm.set( new RemoteFDBRepoSingleTxnSync( ctx, db))
        Success(db.toProto)
    }
  }

  override def handle(command: FDBCrudCommand): Try[FDBCrudResponse] = {
    if( isInitialized ){
      val repo = repoAtm.get()
      repo.execute(command)
    } else {
      Failure( new IllegalStateException("Session is not initialized"))
    }
  }

}

class RemoteFDBStreamingSession(
                                 ctx: FDBRecordContext,
                                 metadataManager: MetadataManagerSync,
                                 responseObserver: StreamObserver[FDBStreamingSessionResponse]
                               ) extends StreamObserver[FDBStreamingSessionComand]{

  val sessionHandler = new RemoteFDBStreamingSessionHandlerSync( ctx, metadataManager)

  override def onNext( command: FDBStreamingSessionComand): Unit = {
    if( command.hasBegin && !sessionHandler.isInitialized) {
      sessionHandler. handle(command.getBegin)
        .map{ resp =>
          FDBStreamingSessionResponse.newBuilder()
            .setDatabase( resp )
            .build()
        }
    } else {
      sessionHandler.handle(command.getExecute)
        .map{ resp =>
          FDBStreamingSessionResponse.newBuilder()
            .setExecResponse( resp )
            .build()
        }
    } match {
      case Failure(ex) => handleError(ex)
      case Success(resp) =>
        responseObserver.onNext( resp )
    }
  }

  def handleError( ex: Throwable ): Unit = {
    responseObserver.onError( ex)
  }

  override def onError(t: Throwable): Unit = {
    responseObserver.onError(t)
  }

  override def onCompleted(): Unit = {
    responseObserver.onCompleted()
  }
}