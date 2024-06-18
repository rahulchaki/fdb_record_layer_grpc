package cio.fbd.record.grpc.server

import cio.fbd.record.rpc.{MetadataManagerSync, RemoteFDBStreamingSession}
import cio.fdb.record.grpc.FDBStreamingSessionGrpc.FDBStreamingSessionImplBase
import cio.fdb.record.grpc.FdbSession.{FDBStreamingSessionComand, FDBStreamingSessionResponse}
import com.apple.foundationdb.record.provider.foundationdb.{FDBDatabase, FDBRecordContext}
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

    val responsePromise = Promise[Boolean]()

    val wrappedResponseObserver = new StreamObserver[FDBStreamingSessionResponse]{
      override def onNext(value: FDBStreamingSessionResponse): Unit = {
        responseObserver.onNext(value)
      }
      override def onError(t: Throwable): Unit = {
        responsePromise.failure(t)
      }
      override def onCompleted(): Unit = {
        responsePromise.success(true)
      }
    }
    val contextSet = Promise[RemoteFDBStreamingSession]()
    val async: Function[_ >: FDBRecordContext, CompletableFuture[_]] = (ctx: FDBRecordContext) => {
      contextSet.success(
        new RemoteFDBStreamingSession( ctx, metadataManager, wrappedResponseObserver )
      )
      responsePromise.future.asJava.toCompletableFuture
    }
    fdb.runAsync(async).asScala.onComplete {
      case Failure(ex) =>
        responseObserver.onError(  Status.fromThrowable(ex).asRuntimeException())
      case Success(_) =>
        responseObserver.onCompleted()
    }
    Await.result( contextSet.future, Duration.Inf )
  }

}
