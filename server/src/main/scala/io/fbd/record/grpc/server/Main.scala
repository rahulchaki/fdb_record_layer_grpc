package io.fbd.record.grpc.server

import com.apple.foundationdb.record.provider.foundationdb.FDBDatabaseFactoryImpl
import io.fbd.record.rpc.MetadataManager
import io.fbd.util.FDBUtil
import io.grpc.ServerBuilder


object Main extends App {
  val factory = new FDBDatabaseFactoryImpl()
  val fdb = factory.getDatabase
  //FDBUtil.clearAll( fdb.database())
  val baseNamespace = List("application", "demo", "environment", "dev")
  val metadataManager = MetadataManager.sync(fdb,baseNamespace)
  val server = ServerBuilder
    .forPort(8080)
    .addService( new FDBMetadataManagerGrpcService(metadataManager))
    .addService( new StreamingSessionGrpcService())
    .addService(new FDBCrudGrpcService(fdb,metadataManager))
    .addService(new FDBStreamingSessionGrpcService(fdb,metadataManager))
    .build()

  server.start()
  println("Server Started")

  server.awaitTermination()
}
