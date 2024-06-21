package io.fbd.record.grpc.server

import io.fdb.record.grpc.FDBMetadataManagerGrpc.FDBMetadataManagerImplBase
import io.fdb.record.grpc.FdbMetadataManager._
import io.fbd.record.rpc.MetadataManagerSync
import io.grpc.Status
import io.grpc.stub.StreamObserver

import scala.util.{Failure, Success}

class FDBMetadataManagerGrpcService(
                                     metadataManager: MetadataManagerSync
                                   ) extends FDBMetadataManagerImplBase{

  override def createOrOpen(
                             request: FDBCreateOrOpenRequest,
                             responseObserver: StreamObserver[FDBCreateOrOpenResponse]
                           ): Unit = {
    metadataManager.createOrOpen(request.getDatabase, request.getSchema) match {
      case Failure(ex) =>
        responseObserver.onError( Status.fromThrowable(ex).asRuntimeException())
      case Success( db ) =>
        responseObserver.onNext(
          FDBCreateOrOpenResponse.newBuilder()
            .setMetadata(db.toProto)
            .build()
        )
        responseObserver.onCompleted()
    }
  }

}
