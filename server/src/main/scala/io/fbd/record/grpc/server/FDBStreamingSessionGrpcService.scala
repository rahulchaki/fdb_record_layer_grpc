package io.fbd.record.grpc.server

import io.fdb.record.grpc.FDBStreamingSessionGrpc.FDBStreamingSessionImplBase
import io.fdb.record.grpc.FdbSession.{FDBStreamingSessionComand, FDBStreamingSessionResponse}
import com.apple.foundationdb.record.provider.foundationdb.{FDBDatabase, FDBRecordContext}
import io.fbd.record.rpc.{FDBStreamingSession, MetadataManagerSync}
import io.grpc.Status
import io.grpc.stub.StreamObserver

import java.util.concurrent.CompletableFuture
import java.util.function.Function
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration.Duration
import scala.concurrent.{Await, Promise}
import scala.jdk.FutureConverters.{CompletionStageOps, FutureOps}
import scala.util.{Failure, Success}

class FDBStreamingSessionGrpcService(
                                            fdb: FDBDatabase,
                                            metadataManager: MetadataManagerSync
                                          ) extends FDBStreamingSessionImplBase{

  override def execute(responseObserver: StreamObserver[FDBStreamingSessionResponse]): StreamObserver[FDBStreamingSessionComand] = {

    val hasProcessingFinished = Promise[Boolean]()

    val wrappedResponseObserver = new StreamObserver[FDBStreamingSessionResponse]{
      override def onNext(value: FDBStreamingSessionResponse): Unit = {
        responseObserver.onNext(value)
      }
      override def onError(t: Throwable): Unit = {
        hasProcessingFinished.failure(t)
      }
      override def onCompleted(): Unit = {
        hasProcessingFinished.success(true)
      }
    }
    val isContextSet = Promise[StreamObserver[FDBStreamingSessionComand]]()

    val processRequests: Function[_ >: FDBRecordContext, CompletableFuture[_]] =
      (ctx: FDBRecordContext) => {
        isContextSet.success(
          FDBStreamingSession.newSession( ctx, metadataManager, wrappedResponseObserver )
        )
        hasProcessingFinished.future.asJava.toCompletableFuture
      }

    fdb.runAsync(processRequests).asScala.onComplete {
      case Failure(ex) =>
        responseObserver.onError(  Status.fromThrowable(ex).asRuntimeException())
      case Success(_) =>
        responseObserver.onCompleted()
    }
    Await.result( isContextSet.future, Duration.Inf )
  }

}
