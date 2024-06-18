package cio.fbd.record.grpc.server

import cio.fbd.record.rpc.MetadataManagerSync
import cio.fdb.record.grpc.FDBMetadataManagerGrpc.FDBMetadataManagerImplBase
import cio.fdb.record.grpc.FdbMetadataManager._
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
