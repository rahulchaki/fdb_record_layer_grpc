package cio.fbd.record.grpc.server

import cio.fbd.util.FDBUtil
import com.apple.foundationdb.record.provider.foundationdb.FDBDatabaseFactoryImpl
import io.grpc.ServerBuilder


object Main extends App {
  val factory = new FDBDatabaseFactoryImpl()
  val fdb = factory.getDatabase
  FDBUtil.clearAll( fdb.database())
  val sessionManager = new RemoteFDBSessionManager(fdb)
  val server = ServerBuilder
    .forPort(8080)
    .addService(new FDBRemoteCRUDGrpcService(fdb))
    .addService(new FDBRemoteInteractiveSessionGrpcService(sessionManager))
    .build()

  server.start()
  println("Server Started")

  server.awaitTermination()
}
