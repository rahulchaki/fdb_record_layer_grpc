package com.cio.fdb.record.grpc.client


import com.apple.foundationdb.KeyValue
import com.cio.fdb.record.grpc.{FDBRemoteGrpc, SampleProto}
import com.cio.fdb.record.grpc.FdbRecordGrpc._
import com.cio.fdb.util.FDBUtil
import io.grpc.ManagedChannelBuilder

import scala.jdk.CollectionConverters._


object Main extends App {
  val channel = ManagedChannelBuilder.forAddress("localhost", 8080)
    .usePlaintext()
    .build()
  val clientId = 1L
  val stub = FDBRemoteGrpc.newBlockingStub(channel)
  val sessionHandle = stub.newSession(
    FDBRemoteSessionRequest.newBuilder().setClientId(clientId).build()
  )

  val recordStore = stub.newRecordStore(
    FDBRemoteRecordStoreRequest.newBuilder()
      .setSession( sessionHandle)
      .setMetadata( SampleProto.getDescriptor.toProto )
      .setKeySpace(
        FDBDirectory.newBuilder()
          .setName("environment")
          .setValue("demo")
          .setSubDirectory( FDBDirectory.newBuilder().setName("application").setValue("ecommerce").build())
          .build()
      )
      .build()
  )
  println(recordStore.toString)

  val result = stub.saveRecord(
    FDBSaveRecordCommand.newBuilder()
      .setStore( recordStore )
      .setTable(  SampleProto.Vendor.getDescriptor.getName )
      .setRecord(
        SampleProto.Vendor.newBuilder()
          .setVendorId(1234L)
          .setVendorName("Tst Vendor ")
          .build().toByteString
      )
      .build()
  )

  println(SampleProto.Vendor.parseFrom(result.getRecord))

  FDBUtil.displayAll(
    stub.getDump(FDBDumpAllCommand.newBuilder().build()).asScala.flatMap { batch =>
      batch.getDataList.asScala.map( kv => new KeyValue(kv.getKey.toByteArray, kv.getValue.toByteArray))
    }.toList
  )

}