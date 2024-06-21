package io.fbd.record.rpc

import io.fdb.record.grpc.FdbCrud.FDBCrudResponse
import io.fdb.record.grpc.FdbSession._
import com.apple.foundationdb.record.provider.foundationdb.FDBRecordContext
import io.grpc.stub.StreamObserver

import java.util.concurrent.{ConcurrentHashMap, ConcurrentSkipListSet, Executor, Executors}
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.{ExecutionContext, Future}
import scala.util.{Failure, Success, Try}


trait RemoteFDBStreamingSessionHandler[M[_]] {
  def handle( command: FDBStreamingSessionComand ): M[FDBStreamingSessionResponse]
}

class RemoteFDBStreamingSessionHandlerAsync(
                                            ctx: FDBRecordContext,
                                            metadataManager: MetadataManagerSync
                                          ) extends RemoteFDBStreamingSessionHandler[Future]{

  private val handlers = new ConcurrentHashMap[String, RemoteFDBRepoSingleTxnAsync]()

  private def handlerOf(dbName: String ): Option[RemoteFDBRepoSingleTxnAsync] = {
    Option(handlers.get(dbName)) match {
      case None =>
        metadataManager.cached(dbName).map{ db =>
          RemoteFDBRepo.singleTxnAsync( ctx, db )
        }
      case ret => ret
    }
  }

  override def handle(command: FDBStreamingSessionComand): Future[FDBStreamingSessionResponse] = {
    handlerOf(command.getDatabase) match {
      case Some( handler ) =>
        handler.execute( command.getCommand ).map{ resp =>
          FDBStreamingSessionResponse.newBuilder()
            .setCommandId( command.getCommandId)
            .setResponse( resp )
            .build()
        }
      case None =>
        Future.failed(
          new IllegalArgumentException(s"No database ${command.getDatabase} locally cached. ")
        )
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

  override def onNext( command: FDBStreamingSessionComand): Unit = {
    val taskF = sessionHandler.handle(command)
    taskFutures.add(taskF)
    taskF.onComplete {
      case Failure(ex) =>
        taskFutures.remove(taskF)
        handleError( command.getCommandId, ex )
      case Success(resp) =>
        taskFutures.remove(taskF)
        responseObserver.onNext( resp )
    }
  }

  private def handleError( commandId: Long , ex: Throwable ): Unit = {
    responseObserver.onNext( FDBStreamingSessionResponse.newBuilder()
      .setCommandId( commandId)
      .setResponse(
        FDBCrudResponse.newBuilder()
          .setError( FDBTransactionErrors( ex))
          .build()
      )
      .build()
    )
  }

  override def onError(t: Throwable): Unit = {}

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
        responseObserver.onError(exception)
      case Success(_) =>
        responseObserver.onCompleted()
    }
    ex.shutdown()
  }
}
