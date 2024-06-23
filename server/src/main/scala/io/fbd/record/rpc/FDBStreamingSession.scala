package io.fbd.record.rpc

import com.apple.foundationdb.record.provider.foundationdb.FDBRecordContext
import io.fdb.record.grpc.FdbCrud.FDBCrudResponse
import io.fdb.record.grpc.FdbSession._
import io.grpc.stub.StreamObserver

import java.util.concurrent.{ConcurrentHashMap, TimeUnit}
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future
import scala.concurrent.duration.Duration


class RemoteFDBStreamingSessionHandlerAsync(
                                            ctx: FDBRecordContext,
                                            metadataManager: MetadataManagerSync
                                          )
  extends StreamingSessionHandler[FDBStreamingSessionComand, FDBStreamingSessionResponse]{

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

object FDBStreamingSession {

implicit object ExtractIdRequest extends ExtractId[FDBStreamingSessionComand]{
  override def extract(command: FDBStreamingSessionComand ): Long = command.getCommandId
}
implicit object FromThrowableResponse extends ObjBuilder[FDBStreamingSessionResponse]{
  override def build( id: Long, ex: Throwable ): FDBStreamingSessionResponse = {
    FDBStreamingSessionResponse.newBuilder()
      .setCommandId( id)
      .setResponse(
        FDBCrudResponse.newBuilder()
          .setError( FDBTransactionErrors( ex))
          .build()
      )
      .build()
  }
}

  def newSession( ctx: FDBRecordContext,
                  metadataManager: MetadataManagerSync,
                  responseObserver: StreamObserver[FDBStreamingSessionResponse]
                ) : StreamingSession[FDBStreamingSessionComand, FDBStreamingSessionResponse] = {
    val handler = new RemoteFDBStreamingSessionHandlerAsync( ctx, metadataManager )
    new StreamingSession[FDBStreamingSessionComand, FDBStreamingSessionResponse](
      responseObserver,
      handler,
      Duration.apply(3, TimeUnit.SECONDS)
    )
  }

}