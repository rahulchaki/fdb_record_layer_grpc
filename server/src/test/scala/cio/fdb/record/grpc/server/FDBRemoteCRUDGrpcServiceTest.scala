package cio.fdb.record.grpc.server

import cio.fbd.record.grpc.server.FDBRemoteCRUDGrpcService
import com.apple.foundationdb.record.provider.foundationdb.FDBDatabaseFactoryImpl

object FDBRemoteCRUDGrpcServiceTest {
  val factory = new FDBDatabaseFactoryImpl()
  val fdb = factory.getDatabase
  val service = new FDBRemoteCRUDGrpcService(fdb)


}
