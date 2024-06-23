package io.fbd.record.grpc.server

import io.fbd.record.rpc.StreamingSessionRaw
import io.fdb.record.grpc.RawStreamingSessionServiceGrpc.RawStreamingSessionServiceImplBase
import io.fdb.record.grpc.StreamingSession
import io.grpc.stub.StreamObserver

class StreamingSessionGrpcService() extends RawStreamingSessionServiceImplBase {
  override def execute(responseObserver: StreamObserver[StreamingSession.StreamingSessionResponse]):
  StreamObserver[StreamingSession.StreamingSessionRequest] = {
    StreamingSessionRaw.ECHO( responseObserver)
  }
}
