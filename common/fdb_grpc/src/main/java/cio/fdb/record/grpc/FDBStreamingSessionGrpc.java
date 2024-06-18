package cio.fdb.record.grpc;

import static io.grpc.MethodDescriptor.generateFullMethodName;

/**
 */
@javax.annotation.Generated(
    value = "by gRPC proto compiler (version 1.64.0)",
    comments = "Source: fdb_session.proto")
@io.grpc.stub.annotations.GrpcGenerated
public final class FDBStreamingSessionGrpc {

  private FDBStreamingSessionGrpc() {}

  public static final java.lang.String SERVICE_NAME = "cio.fdb.record.grpc.FDBStreamingSession";

  // Static method descriptors that strictly reflect the proto.
  private static volatile io.grpc.MethodDescriptor<cio.fdb.record.grpc.FdbSession.FDBStreamingSessionComand,
      cio.fdb.record.grpc.FdbSession.FDBStreamingSessionResponse> getExecuteMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "execute",
      requestType = cio.fdb.record.grpc.FdbSession.FDBStreamingSessionComand.class,
      responseType = cio.fdb.record.grpc.FdbSession.FDBStreamingSessionResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.CLIENT_STREAMING)
  public static io.grpc.MethodDescriptor<cio.fdb.record.grpc.FdbSession.FDBStreamingSessionComand,
      cio.fdb.record.grpc.FdbSession.FDBStreamingSessionResponse> getExecuteMethod() {
    io.grpc.MethodDescriptor<cio.fdb.record.grpc.FdbSession.FDBStreamingSessionComand, cio.fdb.record.grpc.FdbSession.FDBStreamingSessionResponse> getExecuteMethod;
    if ((getExecuteMethod = FDBStreamingSessionGrpc.getExecuteMethod) == null) {
      synchronized (FDBStreamingSessionGrpc.class) {
        if ((getExecuteMethod = FDBStreamingSessionGrpc.getExecuteMethod) == null) {
          FDBStreamingSessionGrpc.getExecuteMethod = getExecuteMethod =
              io.grpc.MethodDescriptor.<cio.fdb.record.grpc.FdbSession.FDBStreamingSessionComand, cio.fdb.record.grpc.FdbSession.FDBStreamingSessionResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.CLIENT_STREAMING)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "execute"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  cio.fdb.record.grpc.FdbSession.FDBStreamingSessionComand.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  cio.fdb.record.grpc.FdbSession.FDBStreamingSessionResponse.getDefaultInstance()))
              .setSchemaDescriptor(new FDBStreamingSessionMethodDescriptorSupplier("execute"))
              .build();
        }
      }
    }
    return getExecuteMethod;
  }

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static FDBStreamingSessionStub newStub(io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<FDBStreamingSessionStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<FDBStreamingSessionStub>() {
        @java.lang.Override
        public FDBStreamingSessionStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new FDBStreamingSessionStub(channel, callOptions);
        }
      };
    return FDBStreamingSessionStub.newStub(factory, channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static FDBStreamingSessionBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<FDBStreamingSessionBlockingStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<FDBStreamingSessionBlockingStub>() {
        @java.lang.Override
        public FDBStreamingSessionBlockingStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new FDBStreamingSessionBlockingStub(channel, callOptions);
        }
      };
    return FDBStreamingSessionBlockingStub.newStub(factory, channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static FDBStreamingSessionFutureStub newFutureStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<FDBStreamingSessionFutureStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<FDBStreamingSessionFutureStub>() {
        @java.lang.Override
        public FDBStreamingSessionFutureStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new FDBStreamingSessionFutureStub(channel, callOptions);
        }
      };
    return FDBStreamingSessionFutureStub.newStub(factory, channel);
  }

  /**
   */
  public interface AsyncService {

    /**
     */
    default io.grpc.stub.StreamObserver<cio.fdb.record.grpc.FdbSession.FDBStreamingSessionComand> execute(
        io.grpc.stub.StreamObserver<cio.fdb.record.grpc.FdbSession.FDBStreamingSessionResponse> responseObserver) {
      return io.grpc.stub.ServerCalls.asyncUnimplementedStreamingCall(getExecuteMethod(), responseObserver);
    }
  }

  /**
   * Base class for the server implementation of the service FDBStreamingSession.
   */
  public static abstract class FDBStreamingSessionImplBase
      implements io.grpc.BindableService, AsyncService {

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return FDBStreamingSessionGrpc.bindService(this);
    }
  }

  /**
   * A stub to allow clients to do asynchronous rpc calls to service FDBStreamingSession.
   */
  public static final class FDBStreamingSessionStub
      extends io.grpc.stub.AbstractAsyncStub<FDBStreamingSessionStub> {
    private FDBStreamingSessionStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected FDBStreamingSessionStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new FDBStreamingSessionStub(channel, callOptions);
    }

    /**
     */
    public io.grpc.stub.StreamObserver<cio.fdb.record.grpc.FdbSession.FDBStreamingSessionComand> execute(
        io.grpc.stub.StreamObserver<cio.fdb.record.grpc.FdbSession.FDBStreamingSessionResponse> responseObserver) {
      return io.grpc.stub.ClientCalls.asyncClientStreamingCall(
          getChannel().newCall(getExecuteMethod(), getCallOptions()), responseObserver);
    }
  }

  /**
   * A stub to allow clients to do synchronous rpc calls to service FDBStreamingSession.
   */
  public static final class FDBStreamingSessionBlockingStub
      extends io.grpc.stub.AbstractBlockingStub<FDBStreamingSessionBlockingStub> {
    private FDBStreamingSessionBlockingStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected FDBStreamingSessionBlockingStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new FDBStreamingSessionBlockingStub(channel, callOptions);
    }
  }

  /**
   * A stub to allow clients to do ListenableFuture-style rpc calls to service FDBStreamingSession.
   */
  public static final class FDBStreamingSessionFutureStub
      extends io.grpc.stub.AbstractFutureStub<FDBStreamingSessionFutureStub> {
    private FDBStreamingSessionFutureStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected FDBStreamingSessionFutureStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new FDBStreamingSessionFutureStub(channel, callOptions);
    }
  }

  private static final int METHODID_EXECUTE = 0;

  private static final class MethodHandlers<Req, Resp> implements
      io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
    private final AsyncService serviceImpl;
    private final int methodId;

    MethodHandlers(AsyncService serviceImpl, int methodId) {
      this.serviceImpl = serviceImpl;
      this.methodId = methodId;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        default:
          throw new AssertionError();
      }
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public io.grpc.stub.StreamObserver<Req> invoke(
        io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_EXECUTE:
          return (io.grpc.stub.StreamObserver<Req>) serviceImpl.execute(
              (io.grpc.stub.StreamObserver<cio.fdb.record.grpc.FdbSession.FDBStreamingSessionResponse>) responseObserver);
        default:
          throw new AssertionError();
      }
    }
  }

  public static final io.grpc.ServerServiceDefinition bindService(AsyncService service) {
    return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
        .addMethod(
          getExecuteMethod(),
          io.grpc.stub.ServerCalls.asyncClientStreamingCall(
            new MethodHandlers<
              cio.fdb.record.grpc.FdbSession.FDBStreamingSessionComand,
              cio.fdb.record.grpc.FdbSession.FDBStreamingSessionResponse>(
                service, METHODID_EXECUTE)))
        .build();
  }

  private static abstract class FDBStreamingSessionBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoFileDescriptorSupplier, io.grpc.protobuf.ProtoServiceDescriptorSupplier {
    FDBStreamingSessionBaseDescriptorSupplier() {}

    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return cio.fdb.record.grpc.FdbSession.getDescriptor();
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.ServiceDescriptor getServiceDescriptor() {
      return getFileDescriptor().findServiceByName("FDBStreamingSession");
    }
  }

  private static final class FDBStreamingSessionFileDescriptorSupplier
      extends FDBStreamingSessionBaseDescriptorSupplier {
    FDBStreamingSessionFileDescriptorSupplier() {}
  }

  private static final class FDBStreamingSessionMethodDescriptorSupplier
      extends FDBStreamingSessionBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoMethodDescriptorSupplier {
    private final java.lang.String methodName;

    FDBStreamingSessionMethodDescriptorSupplier(java.lang.String methodName) {
      this.methodName = methodName;
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.MethodDescriptor getMethodDescriptor() {
      return getServiceDescriptor().findMethodByName(methodName);
    }
  }

  private static volatile io.grpc.ServiceDescriptor serviceDescriptor;

  public static io.grpc.ServiceDescriptor getServiceDescriptor() {
    io.grpc.ServiceDescriptor result = serviceDescriptor;
    if (result == null) {
      synchronized (FDBStreamingSessionGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new FDBStreamingSessionFileDescriptorSupplier())
              .addMethod(getExecuteMethod())
              .build();
        }
      }
    }
    return result;
  }
}
