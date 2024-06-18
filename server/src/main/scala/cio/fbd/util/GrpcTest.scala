package cio.fbd.util

import cio.fdb.record.grpc.FDBRemoteCRUDGrpc
import io.grpc.ManagedChannelBuilder

object GrpcTest {

  val channel = ManagedChannelBuilder.forAddress("localhost", 8080)
    .build()
  val stub = FDBRemoteCRUDGrpc.newBlockingStub(channel)

}
