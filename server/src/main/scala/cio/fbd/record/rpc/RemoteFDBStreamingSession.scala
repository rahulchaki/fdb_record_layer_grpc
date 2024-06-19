package cio.fbd.record.rpc

import cio.fdb.record.grpc.FdbCrud.{FDBCrudCommand, FDBCrudResponse}
import cio.fdb.record.grpc.FdbMetadataManager.FDBDatabaseMetadata
import cio.fdb.record.grpc.FdbSession._
import com.apple.foundationdb.record.provider.foundationdb.FDBRecordContext
import io.grpc.stub.StreamObserver

import java.util.concurrent.atomic.AtomicReference
import java.util.concurrent.{ConcurrentSkipListSet, Executor, Executors}
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.{ExecutionContext, Future}
import scala.util.{Failure, Success, Try}


trait RemoteFDBStreamingSessionHandler[M[_]] {
  def isInitialized: Boolean
  def handle( command: FDBNewStreamingSessionComand ): M[FDBDatabaseMetadata]
  def handle( command: FDBCrudCommand ): M[FDBCrudResponse]
}

class RemoteFDBStreamingSessionHandlerAsync(
                                            ctx: FDBRecordContext,
                                            metadataManager: MetadataManagerSync
                                          ) extends RemoteFDBStreamingSessionHandler[Future]{

  private val repoAtm = new AtomicReference[RemoteFDBRepoSingleTxnAsync]()


  override def isInitialized: Boolean = Option(repoAtm.get()).isDefined

  override def handle(command: FDBNewStreamingSessionComand): Future[FDBDatabaseMetadata] = {
    val dbName = command.getDatabase
    metadataManager.cached(dbName) match {
      case None =>
        Future.failed( new IllegalStateException(s" Metadata for $dbName could not be opened ") )
      case Some( db ) =>
        repoAtm.set( new RemoteFDBRepoSingleTxnAsync( ctx, db))
        Future.successful(db.toProto)
    }
  }

  override def handle(command: FDBCrudCommand): Future[FDBCrudResponse] = {
    if( isInitialized ){
      val repo = repoAtm.get()
      repo.execute(command)
    } else {
      Future.failed( new IllegalStateException("Session is not initialized"))
    }
  }

}

class RemoteFDBStreamingSession(
                                 ctx: FDBRecordContext,
                                 metadataManager: MetadataManagerSync,
                                 responseObserver: StreamObserver[FDBStreamingSessionResponse]
                               ) extends StreamObserver[FDBStreamingSessionComand]{

  private val sessionHandler = new RemoteFDBStreamingSessionHandlerAsync( ctx, metadataManager)

  private val taskFutures = new ConcurrentSkipListSet[Future[_]]()

  private def handle(command: FDBStreamingSessionComand ): Future[ FDBStreamingSessionResponse ] = {
    if( command.hasBegin && !sessionHandler.isInitialized) {
      sessionHandler.handle(command.getBegin)
        .map{ resp =>
          FDBStreamingSessionResponse.newBuilder()
            .setCommandId( command.getCommandId)
            .setDatabase( resp )
            .build()
        }
    } else {
      sessionHandler.handle(command.getExecute)
        .map{ resp =>
          FDBStreamingSessionResponse.newBuilder()
            .setCommandId( command.getCommandId)
            .setExecResponse( resp )
            .build()
        }
    }
  }

  override def onNext( command: FDBStreamingSessionComand): Unit = {
    val taskF = handle( command )
    taskFutures.add(taskF)
    taskF.andThen {
      case Failure(ex) =>
        taskFutures.remove(taskF)
        handleError( ex )
      case Success(resp) =>
        taskFutures.remove(taskF)
        responseObserver.onNext( resp )
    }
  }

  private def handleError(ex: Throwable ): Unit = {
    responseObserver.onError( ex)
  }

  override def onError(t: Throwable): Unit = {
    responseObserver.onError(t)
  }

  private def awaitFinish( timeoutMillis: Long, ex: Executor ): Future[_] = {
    Future{
      val now = System.currentTimeMillis()
      while( taskFutures.size() > 0 && ( System.currentTimeMillis() - now) < timeoutMillis ){
        Try{
          Thread.sleep(taskFutures.size()* 10)
        }
      }
    }(ExecutionContext.fromExecutor(ex))
  }

  override def onCompleted(): Unit = {
    val ex = Executors.newSingleThreadExecutor()
    awaitFinish( 3000, ex ).andThen {
      case Failure(exception) =>
        handleError(exception)
      case Success(_) =>
        responseObserver.onCompleted()
    }
    ex.shutdown()
  }
}
