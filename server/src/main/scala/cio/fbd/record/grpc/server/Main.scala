package cio.fbd.record.grpc.server

import com.apple.foundationdb.record.provider.foundationdb.FDBDatabaseFactoryImpl
import io.grpc.ServerBuilder


object Main extends App {

  val factory = new FDBDatabaseFactoryImpl()
  val fdb = factory.getDatabase
  val sessionManager = new RemoteFDBSessionManager(fdb)
    val server = ServerBuilder
      .forPort(8080)
      .addService(new FDBRemoteGrpcService( sessionManager ))
      .build()

    server.start()
    println("Server Started")
    server.awaitTermination()
}
