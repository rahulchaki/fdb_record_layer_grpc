package cio.fbd.record.grpc.server

import cio.fdb.record.grpc.FDBRemoteCRUDGrpc.FDBRemoteCRUDImplBase
import cio.fdb.record.grpc.FdbRecordGrpc._
import com.apple.foundationdb.record.provider.foundationdb.FDBDatabase
import io.grpc.Status
import io.grpc.stub.StreamObserver

import java.util.concurrent.atomic.AtomicReference
import scala.concurrent.Await
import scala.concurrent.duration.Duration
import scala.jdk.CollectionConverters._
import scala.util.{Failure, Success, Try}

class FDBRemoteCRUDGrpcService(fdb: FDBDatabase) extends FDBRemoteCRUDImplBase {

  private val remoteFdbAtm = new AtomicReference[RemoteFDB]();

  override def loadMetadata(request: FDBLoadMetadataCommand, responseObserver: StreamObserver[FDBLoadMetadataResponse]): Unit = {
    RemoteFDB.loadExisting(fdb, request.getPathList.asScala.toList) match {
      case Some(remoteFdb) =>
        remoteFdbAtm.set(remoteFdb)
        responseObserver.onNext(
          FDBLoadMetadataResponse.newBuilder().setMetadata(remoteFdb.metadata.toProto).build()
        )
      case None =>
        responseObserver.onNext(
          FDBLoadMetadataResponse.newBuilder().build()
        )
    }
    responseObserver.onCompleted()
  }

  override def registerSchema(request: FDBRegisterSchemaCommand, responseObserver: StreamObserver[FDBLoadMetadataResponse]): Unit = {
    Try(RemoteFDB.buildNew(fdb, request.getPathList.asScala.toList, request.getSchema)) match {
      case Failure(ex) =>
        ex.printStackTrace()
        responseObserver.onError(Status.fromThrowable(ex).asRuntimeException())
      case Success(remoteFDB) =>
        remoteFdbAtm.set(remoteFDB)
        responseObserver.onNext(
          FDBLoadMetadataResponse.newBuilder().setMetadata(remoteFDB.metadata.toProto).build()
        )
        responseObserver.onCompleted()
    }
  }

  override def execute(request: FDBCRUDCommand, responseObserver: StreamObserver[FDBCRUDResponse]): Unit = {
    val table = request.getTable
    Option(remoteFdbAtm.get()) match {
      case Some(remoteFDB) =>
        val responseBuilder = FDBCRUDResponse.newBuilder()
        Try {
          request.getOperation match {
            case FDBCRUDCommand.OPERATION.CREATE =>
              if (request.hasRecords) {
                responseBuilder.setKeys(
                  KeysList.newBuilder().addAllKeys(
                    remoteFDB
                      .createAll(table, request.getRecords.getRecordsList.asScala.toList).asJava
                  ).build()
                ).build()
              }
              else
                throw new IllegalArgumentException(" CREATE command needs list of records as data. ")

            case FDBCRUDCommand.OPERATION.LOAD =>
              if (request.hasKeys) {
                responseBuilder.setRecords(
                  RecordsList.newBuilder().addAllRecords(
                    remoteFDB
                      .loadAll(table, request.getKeys.getKeysList.asScala.toList).asJava
                  ).build()
                ).build()
              }
              else if (request.hasQuery) {
                val result = Await.result(remoteFDB.loadAll(table, request.getQuery), Duration.Inf)
                responseBuilder.setRecords(
                  RecordsList.newBuilder().addAllRecords(result.asJava).build()
                ).build()
              }
              else
                throw new IllegalArgumentException(" LOAD command needs either Query or list of keys  as data. ")
            case FDBCRUDCommand.OPERATION.DELETE =>
              if (request.hasKeys) {
                responseBuilder.setSuccess(
                  remoteFDB
                    .deleteAll(table, request.getKeys.getKeysList.asScala.toList)
                ).build()
              }
              else if (request.hasQuery) {
                responseBuilder.setSuccess(
                  remoteFDB
                    .deleteAll(table, request.getQuery)
                ).build()
              }
              else
                throw new IllegalArgumentException(" DELETE command needs either Query or list of keys  as data. ")
            case FDBCRUDCommand.OPERATION.UNRECOGNIZED =>
              throw new IllegalArgumentException(" UNKNOWN command found  ")
          }
        } match {
          case Failure(ex) =>
            ex.printStackTrace()
            responseObserver.onError(Status.fromThrowable(ex).asRuntimeException())
          case Success(response) =>
            responseObserver.onNext(response)
            responseObserver.onCompleted()
        }

      case None =>
        val ex = new IllegalStateException(" Metadata is not loaded ")
        ex.printStackTrace()
        responseObserver.onError(Status.fromThrowable(ex).asRuntimeException())
    }
  }
}
