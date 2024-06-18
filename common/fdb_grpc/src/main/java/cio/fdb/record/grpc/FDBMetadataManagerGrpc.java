package cio.fdb.record.grpc;

import static io.grpc.MethodDescriptor.generateFullMethodName;

/**
 */
@javax.annotation.Generated(
    value = "by gRPC proto compiler (version 1.64.0)",
    comments = "Source: fdb_metadata_manager.proto")
@io.grpc.stub.annotations.GrpcGenerated
public final class FDBMetadataManagerGrpc {

  private FDBMetadataManagerGrpc() {}

  public static final java.lang.String SERVICE_NAME = "cio.fdb.record.grpc.FDBMetadataManager";

  // Static method descriptors that strictly reflect the proto.
  private static volatile io.grpc.MethodDescriptor<cio.fdb.record.grpc.FdbMetadataManager.FDBCreateOrOpenRequest,
      cio.fdb.record.grpc.FdbMetadataManager.FDBCreateOrOpenResponse> getCreateOrOpenMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "CreateOrOpen",
      requestType = cio.fdb.record.grpc.FdbMetadataManager.FDBCreateOrOpenRequest.class,
      responseType = cio.fdb.record.grpc.FdbMetadataManager.FDBCreateOrOpenResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<cio.fdb.record.grpc.FdbMetadataManager.FDBCreateOrOpenRequest,
      cio.fdb.record.grpc.FdbMetadataManager.FDBCreateOrOpenResponse> getCreateOrOpenMethod() {
    io.grpc.MethodDescriptor<cio.fdb.record.grpc.FdbMetadataManager.FDBCreateOrOpenRequest, cio.fdb.record.grpc.FdbMetadataManager.FDBCreateOrOpenResponse> getCreateOrOpenMethod;
    if ((getCreateOrOpenMethod = FDBMetadataManagerGrpc.getCreateOrOpenMethod) == null) {
      synchronized (FDBMetadataManagerGrpc.class) {
        if ((getCreateOrOpenMethod = FDBMetadataManagerGrpc.getCreateOrOpenMethod) == null) {
          FDBMetadataManagerGrpc.getCreateOrOpenMethod = getCreateOrOpenMethod =
              io.grpc.MethodDescriptor.<cio.fdb.record.grpc.FdbMetadataManager.FDBCreateOrOpenRequest, cio.fdb.record.grpc.FdbMetadataManager.FDBCreateOrOpenResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "CreateOrOpen"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  cio.fdb.record.grpc.FdbMetadataManager.FDBCreateOrOpenRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  cio.fdb.record.grpc.FdbMetadataManager.FDBCreateOrOpenResponse.getDefaultInstance()))
              .setSchemaDescriptor(new FDBMetadataManagerMethodDescriptorSupplier("CreateOrOpen"))
              .build();
        }
      }
    }
    return getCreateOrOpenMethod;
  }

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static FDBMetadataManagerStub newStub(io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<FDBMetadataManagerStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<FDBMetadataManagerStub>() {
        @java.lang.Override
        public FDBMetadataManagerStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new FDBMetadataManagerStub(channel, callOptions);
        }
      };
    return FDBMetadataManagerStub.newStub(factory, channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static FDBMetadataManagerBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<FDBMetadataManagerBlockingStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<FDBMetadataManagerBlockingStub>() {
        @java.lang.Override
        public FDBMetadataManagerBlockingStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new FDBMetadataManagerBlockingStub(channel, callOptions);
        }
      };
    return FDBMetadataManagerBlockingStub.newStub(factory, channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static FDBMetadataManagerFutureStub newFutureStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<FDBMetadataManagerFutureStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<FDBMetadataManagerFutureStub>() {
        @java.lang.Override
        public FDBMetadataManagerFutureStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new FDBMetadataManagerFutureStub(channel, callOptions);
        }
      };
    return FDBMetadataManagerFutureStub.newStub(factory, channel);
  }

  /**
   */
  public interface AsyncService {

    /**
     */
    default void createOrOpen(cio.fdb.record.grpc.FdbMetadataManager.FDBCreateOrOpenRequest request,
        io.grpc.stub.StreamObserver<cio.fdb.record.grpc.FdbMetadataManager.FDBCreateOrOpenResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getCreateOrOpenMethod(), responseObserver);
    }
  }

  /**
   * Base class for the server implementation of the service FDBMetadataManager.
   */
  public static abstract class FDBMetadataManagerImplBase
      implements io.grpc.BindableService, AsyncService {

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return FDBMetadataManagerGrpc.bindService(this);
    }
  }

  /**
   * A stub to allow clients to do asynchronous rpc calls to service FDBMetadataManager.
   */
  public static final class FDBMetadataManagerStub
      extends io.grpc.stub.AbstractAsyncStub<FDBMetadataManagerStub> {
    private FDBMetadataManagerStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected FDBMetadataManagerStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new FDBMetadataManagerStub(channel, callOptions);
    }

    /**
     */
    public void createOrOpen(cio.fdb.record.grpc.FdbMetadataManager.FDBCreateOrOpenRequest request,
        io.grpc.stub.StreamObserver<cio.fdb.record.grpc.FdbMetadataManager.FDBCreateOrOpenResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getCreateOrOpenMethod(), getCallOptions()), request, responseObserver);
    }
  }

  /**
   * A stub to allow clients to do synchronous rpc calls to service FDBMetadataManager.
   */
  public static final class FDBMetadataManagerBlockingStub
      extends io.grpc.stub.AbstractBlockingStub<FDBMetadataManagerBlockingStub> {
    private FDBMetadataManagerBlockingStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected FDBMetadataManagerBlockingStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new FDBMetadataManagerBlockingStub(channel, callOptions);
    }

    /**
     */
    public cio.fdb.record.grpc.FdbMetadataManager.FDBCreateOrOpenResponse createOrOpen(cio.fdb.record.grpc.FdbMetadataManager.FDBCreateOrOpenRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getCreateOrOpenMethod(), getCallOptions(), request);
    }
  }

  /**
   * A stub to allow clients to do ListenableFuture-style rpc calls to service FDBMetadataManager.
   */
  public static final class FDBMetadataManagerFutureStub
      extends io.grpc.stub.AbstractFutureStub<FDBMetadataManagerFutureStub> {
    private FDBMetadataManagerFutureStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected FDBMetadataManagerFutureStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new FDBMetadataManagerFutureStub(channel, callOptions);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<cio.fdb.record.grpc.FdbMetadataManager.FDBCreateOrOpenResponse> createOrOpen(
        cio.fdb.record.grpc.FdbMetadataManager.FDBCreateOrOpenRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getCreateOrOpenMethod(), getCallOptions()), request);
    }
  }

  private static final int METHODID_CREATE_OR_OPEN = 0;

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
        case METHODID_CREATE_OR_OPEN:
          serviceImpl.createOrOpen((cio.fdb.record.grpc.FdbMetadataManager.FDBCreateOrOpenRequest) request,
              (io.grpc.stub.StreamObserver<cio.fdb.record.grpc.FdbMetadataManager.FDBCreateOrOpenResponse>) responseObserver);
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
          getCreateOrOpenMethod(),
          io.grpc.stub.ServerCalls.asyncUnaryCall(
            new MethodHandlers<
              cio.fdb.record.grpc.FdbMetadataManager.FDBCreateOrOpenRequest,
              cio.fdb.record.grpc.FdbMetadataManager.FDBCreateOrOpenResponse>(
                service, METHODID_CREATE_OR_OPEN)))
        .build();
  }

  private static abstract class FDBMetadataManagerBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoFileDescriptorSupplier, io.grpc.protobuf.ProtoServiceDescriptorSupplier {
    FDBMetadataManagerBaseDescriptorSupplier() {}

    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return cio.fdb.record.grpc.FdbMetadataManager.getDescriptor();
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.ServiceDescriptor getServiceDescriptor() {
      return getFileDescriptor().findServiceByName("FDBMetadataManager");
    }
  }

  private static final class FDBMetadataManagerFileDescriptorSupplier
      extends FDBMetadataManagerBaseDescriptorSupplier {
    FDBMetadataManagerFileDescriptorSupplier() {}
  }

  private static final class FDBMetadataManagerMethodDescriptorSupplier
      extends FDBMetadataManagerBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoMethodDescriptorSupplier {
    private final java.lang.String methodName;

    FDBMetadataManagerMethodDescriptorSupplier(java.lang.String methodName) {
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
      synchronized (FDBMetadataManagerGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new FDBMetadataManagerFileDescriptorSupplier())
              .addMethod(getCreateOrOpenMethod())
              .build();
        }
      }
    }
    return result;
  }
}
