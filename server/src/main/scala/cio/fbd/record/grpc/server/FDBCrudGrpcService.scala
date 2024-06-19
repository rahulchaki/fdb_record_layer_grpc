package cio.fbd.record.grpc.server

import cio.fbd.record.rpc.{MetadataManagerSync, RemoteFDBRepo}
import cio.fdb.record.grpc.FDBCrudGrpc.FDBCrudImplBase
import cio.fdb.record.grpc.FdbCrud._
import com.apple.foundationdb.record.provider.foundationdb.FDBDatabase
import io.grpc.Status
import io.grpc.stub.StreamObserver

import scala.concurrent.ExecutionContext.Implicits.global
import scala.util.{Failure, Success}

class FDBCrudGrpcService(
                          fdb: FDBDatabase,
                          metadataManager: MetadataManagerSync
                        ) extends FDBCrudImplBase {

  override def execute(request: FDBCrudCommand, responseObserver: StreamObserver[FDBCrudResponse]): Unit = {
    if( request.hasDatabase ) {
      metadataManager.open(request.getDatabase) match {
        case Failure(ex) =>
          responseObserver.onError(Status.fromThrowable(ex).asRuntimeException())
        case Success(None) =>
          responseObserver.onError(
            Status.fromThrowable(
              new IllegalArgumentException(s" No metadata found for db ${request.getDatabase} ")
            ).asRuntimeException()
          )
        case Success( Some(db) ) =>
          RemoteFDBRepo.async(fdb, db)
            .execute(request).onComplete {
            case Failure(ex) =>
              responseObserver.onError(Status.fromThrowable(ex).asRuntimeException())
            case Success(value) =>
              responseObserver.onNext(value)
              responseObserver.onCompleted()
          }
      }
    }else {
      responseObserver.onError(
        Status.fromThrowable( new IllegalArgumentException(" No database found in the request ")).asRuntimeException()
      )
    }
  }
}
