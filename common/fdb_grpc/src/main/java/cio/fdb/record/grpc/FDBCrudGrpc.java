package cio.fdb.record.grpc;

import static io.grpc.MethodDescriptor.generateFullMethodName;

/**
 */
@javax.annotation.Generated(
    value = "by gRPC proto compiler (version 1.64.0)",
    comments = "Source: fdb_crud.proto")
@io.grpc.stub.annotations.GrpcGenerated
public final class FDBCrudGrpc {

  private FDBCrudGrpc() {}

  public static final java.lang.String SERVICE_NAME = "cio.fdb.record.grpc.FDBCrud";

  // Static method descriptors that strictly reflect the proto.
  private static volatile io.grpc.MethodDescriptor<cio.fdb.record.grpc.FdbCrud.FDBCrudCommand,
      cio.fdb.record.grpc.FdbCrud.FDBCrudResponse> getExecuteMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "execute",
      requestType = cio.fdb.record.grpc.FdbCrud.FDBCrudCommand.class,
      responseType = cio.fdb.record.grpc.FdbCrud.FDBCrudResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<cio.fdb.record.grpc.FdbCrud.FDBCrudCommand,
      cio.fdb.record.grpc.FdbCrud.FDBCrudResponse> getExecuteMethod() {
    io.grpc.MethodDescriptor<cio.fdb.record.grpc.FdbCrud.FDBCrudCommand, cio.fdb.record.grpc.FdbCrud.FDBCrudResponse> getExecuteMethod;
    if ((getExecuteMethod = FDBCrudGrpc.getExecuteMethod) == null) {
      synchronized (FDBCrudGrpc.class) {
        if ((getExecuteMethod = FDBCrudGrpc.getExecuteMethod) == null) {
          FDBCrudGrpc.getExecuteMethod = getExecuteMethod =
              io.grpc.MethodDescriptor.<cio.fdb.record.grpc.FdbCrud.FDBCrudCommand, cio.fdb.record.grpc.FdbCrud.FDBCrudResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "execute"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  cio.fdb.record.grpc.FdbCrud.FDBCrudCommand.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  cio.fdb.record.grpc.FdbCrud.FDBCrudResponse.getDefaultInstance()))
              .setSchemaDescriptor(new FDBCrudMethodDescriptorSupplier("execute"))
              .build();
        }
      }
    }
    return getExecuteMethod;
  }

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static FDBCrudStub newStub(io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<FDBCrudStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<FDBCrudStub>() {
        @java.lang.Override
        public FDBCrudStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new FDBCrudStub(channel, callOptions);
        }
      };
    return FDBCrudStub.newStub(factory, channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static FDBCrudBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<FDBCrudBlockingStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<FDBCrudBlockingStub>() {
        @java.lang.Override
        public FDBCrudBlockingStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new FDBCrudBlockingStub(channel, callOptions);
        }
      };
    return FDBCrudBlockingStub.newStub(factory, channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static FDBCrudFutureStub newFutureStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<FDBCrudFutureStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<FDBCrudFutureStub>() {
        @java.lang.Override
        public FDBCrudFutureStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new FDBCrudFutureStub(channel, callOptions);
        }
      };
    return FDBCrudFutureStub.newStub(factory, channel);
  }

  /**
   */
  public interface AsyncService {

    /**
     */
    default void execute(cio.fdb.record.grpc.FdbCrud.FDBCrudCommand request,
        io.grpc.stub.StreamObserver<cio.fdb.record.grpc.FdbCrud.FDBCrudResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getExecuteMethod(), responseObserver);
    }
  }

  /**
   * Base class for the server implementation of the service FDBCrud.
   */
  public static abstract class FDBCrudImplBase
      implements io.grpc.BindableService, AsyncService {

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return FDBCrudGrpc.bindService(this);
    }
  }

  /**
   * A stub to allow clients to do asynchronous rpc calls to service FDBCrud.
   */
  public static final class FDBCrudStub
      extends io.grpc.stub.AbstractAsyncStub<FDBCrudStub> {
    private FDBCrudStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected FDBCrudStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new FDBCrudStub(channel, callOptions);
    }

    /**
     */
    public void execute(cio.fdb.record.grpc.FdbCrud.FDBCrudCommand request,
        io.grpc.stub.StreamObserver<cio.fdb.record.grpc.FdbCrud.FDBCrudResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getExecuteMethod(), getCallOptions()), request, responseObserver);
    }
  }

  /**
   * A stub to allow clients to do synchronous rpc calls to service FDBCrud.
   */
  public static final class FDBCrudBlockingStub
      extends io.grpc.stub.AbstractBlockingStub<FDBCrudBlockingStub> {
    private FDBCrudBlockingStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected FDBCrudBlockingStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new FDBCrudBlockingStub(channel, callOptions);
    }

    /**
     */
    public cio.fdb.record.grpc.FdbCrud.FDBCrudResponse execute(cio.fdb.record.grpc.FdbCrud.FDBCrudCommand request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getExecuteMethod(), getCallOptions(), request);
    }
  }

  /**
   * A stub to allow clients to do ListenableFuture-style rpc calls to service FDBCrud.
   */
  public static final class FDBCrudFutureStub
      extends io.grpc.stub.AbstractFutureStub<FDBCrudFutureStub> {
    private FDBCrudFutureStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected FDBCrudFutureStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new FDBCrudFutureStub(channel, callOptions);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<cio.fdb.record.grpc.FdbCrud.FDBCrudResponse> execute(
        cio.fdb.record.grpc.FdbCrud.FDBCrudCommand request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getExecuteMethod(), getCallOptions()), request);
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
        case METHODID_EXECUTE:
          serviceImpl.execute((cio.fdb.record.grpc.FdbCrud.FDBCrudCommand) request,
              (io.grpc.stub.StreamObserver<cio.fdb.record.grpc.FdbCrud.FDBCrudResponse>) responseObserver);
          break;
        default:
          throw new AssertionError();
      }
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public io.grpc.stub.StreamObserver<Req> invoke(
        io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        default:
          throw new AssertionError();
      }
    }
  }

  public static final io.grpc.ServerServiceDefinition bindService(AsyncService service) {
    return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
        .addMethod(
          getExecuteMethod(),
          io.grpc.stub.ServerCalls.asyncUnaryCall(
            new MethodHandlers<
              cio.fdb.record.grpc.FdbCrud.FDBCrudCommand,
              cio.fdb.record.grpc.FdbCrud.FDBCrudResponse>(
                service, METHODID_EXECUTE)))
        .build();
  }

  private static abstract class FDBCrudBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoFileDescriptorSupplier, io.grpc.protobuf.ProtoServiceDescriptorSupplier {
    FDBCrudBaseDescriptorSupplier() {}

    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return cio.fdb.record.grpc.FdbCrud.getDescriptor();
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.ServiceDescriptor getServiceDescriptor() {
      return getFileDescriptor().findServiceByName("FDBCrud");
    }
  }

  private static final class FDBCrudFileDescriptorSupplier
      extends FDBCrudBaseDescriptorSupplier {
    FDBCrudFileDescriptorSupplier() {}
  }

  private static final class FDBCrudMethodDescriptorSupplier
      extends FDBCrudBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoMethodDescriptorSupplier {
    private final java.lang.String methodName;

    FDBCrudMethodDescriptorSupplier(java.lang.String methodName) {
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
      synchronized (FDBCrudGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new FDBCrudFileDescriptorSupplier())
              .addMethod(getExecuteMethod())
              .build();
        }
      }
    }
    return result;
  }
}
