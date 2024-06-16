package cio.fbd.record.grpc.server

import cio.fdb.record.grpc.FDBRemoteGrpc._
import cio.fdb.record.grpc.FdbRecordGrpc._
import io.grpc.stub.StreamObserver

import scala.concurrent.ExecutionContext.Implicits.global
import scala.jdk.CollectionConverters._
import scala.util.{Failure, Success}

class FDBRemoteGrpcService(sessionsManager: RemoteFDBSessionManager) extends FDBRemoteImplBase {

  override def newSession(
                           request: FDBRemoteSessionRequest,
                           responseObserver: StreamObserver[FDBRemoteSessionHandle]
                         ): Unit = {
    sessionsManager.newSession().onComplete {
      case Failure(ex) =>
        responseObserver.onError(ex)
        responseObserver.onCompleted()
      case Success(sessionId) =>
        responseObserver.onNext(
          FDBRemoteSessionHandle.newBuilder().setId(sessionId).build()
        )
        responseObserver.onCompleted()

    }

  }

  override def commit(
                       request: FDBRemoteSessionCommitRequest,
                       responseObserver: StreamObserver[FDBRemoteSessionCommitResponse]
                     ): Unit = {
    sessionsManager.commit(request.getSession.getId).onComplete {
      case Failure(ex) =>
        responseObserver.onError(ex)
        responseObserver.onCompleted()
      case Success(_) =>
        responseObserver.onNext(
          FDBRemoteSessionCommitResponse.newBuilder()
            .setResult(TransactionCommitResult.TRANSACTION_SUCCESS)
            .build()
        )
        responseObserver.onCompleted()
    }
  }

  override def newRecordStore(
                               request: FDBRemoteRecordStoreRequest,
                               responseObserver: StreamObserver[FDBRemoteRecordStoreHandle]
                             ): Unit = {
    sessionsManager
      .newRecordStore(request.getSession.getId, request.getMetadata, request.getKeySpace).onComplete {
        case Failure(ex) =>
          responseObserver.onError(ex)
          responseObserver.onCompleted()
        case Success(storeHeader) =>
          responseObserver.onNext(
            FDBRemoteRecordStoreHandle.newBuilder()
              .setSession(FDBRemoteSessionHandle.newBuilder().setId(request.getSession.getId).build())
              .setId(request.getSession.getId)
              .setStore(storeHeader)
              .build()
          )
          responseObserver.onCompleted()

      }
  }

  override def saveRecord(request: FDBSaveRecordCommand, responseObserver: StreamObserver[FDBSaveRecordResult]): Unit = {
    sessionsManager.saveRecord(request.getStore.getSession.getId, request.getTable, request.getRecord).onComplete {
      case Failure(ex) =>
        responseObserver.onError(ex)
        responseObserver.onCompleted()
      case Success(record) =>
        responseObserver.onNext(
          FDBSaveRecordResult.newBuilder().setRecord(record.toByteString).build()
        )
        responseObserver.onCompleted()
    }
  }

  override def getDump(request: FDBDumpAllCommand, responseObserver: StreamObserver[FDBDumpAllResponseBatch]): Unit = {
    val results = if( request.hasStart && request.hasEnd )
      sessionsManager.getRange( request.getStart, request.getEnd )
    else sessionsManager.getAll
    results.foreach{ batch =>
      responseObserver.onNext(
        FDBDumpAllResponseBatch.newBuilder()
          .addAllData(
            batch.map{ kv =>
              FDBDumpAllResponseBatch.KV.newBuilder()
                .setKey( kv._1)
                .setValue( kv._2 )
                .build()
            }.asJava
          )
          .build()
      )
    }
    responseObserver.onCompleted()
  }

  override def deleteRecord(request: FDBDeleteRecordCommand, responseObserver: StreamObserver[FDBDeleteRecordResult]): Unit = {
    sessionsManager.deleteRecord(request.getStore.getId, request.getRecordId).onComplete {
      case Failure(ex) =>
        responseObserver.onError(ex)
        responseObserver.onCompleted()
      case Success(result) =>
        responseObserver.onNext(
          FDBDeleteRecordResult.newBuilder().setResult(result).build()
        )
        responseObserver.onCompleted()
    }
  }

}
