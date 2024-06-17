package cio.fdb.record.grpc;

import static io.grpc.MethodDescriptor.generateFullMethodName;

/**
 */
@javax.annotation.Generated(
    value = "by gRPC proto compiler (version 1.64.0)",
    comments = "Source: fdb_record_grpc.proto")
@io.grpc.stub.annotations.GrpcGenerated
public final class FDBRemoteCRUDGrpc {

  private FDBRemoteCRUDGrpc() {}

  public static final java.lang.String SERVICE_NAME = "cio.fdb.record.grpc.FDBRemoteCRUD";

  // Static method descriptors that strictly reflect the proto.
  private static volatile io.grpc.MethodDescriptor<cio.fdb.record.grpc.FdbRecordGrpc.FDBLoadMetadataCommand,
      cio.fdb.record.grpc.FdbRecordGrpc.FDBLoadMetadataResponse> getLoadMetadataMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "LoadMetadata",
      requestType = cio.fdb.record.grpc.FdbRecordGrpc.FDBLoadMetadataCommand.class,
      responseType = cio.fdb.record.grpc.FdbRecordGrpc.FDBLoadMetadataResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<cio.fdb.record.grpc.FdbRecordGrpc.FDBLoadMetadataCommand,
      cio.fdb.record.grpc.FdbRecordGrpc.FDBLoadMetadataResponse> getLoadMetadataMethod() {
    io.grpc.MethodDescriptor<cio.fdb.record.grpc.FdbRecordGrpc.FDBLoadMetadataCommand, cio.fdb.record.grpc.FdbRecordGrpc.FDBLoadMetadataResponse> getLoadMetadataMethod;
    if ((getLoadMetadataMethod = FDBRemoteCRUDGrpc.getLoadMetadataMethod) == null) {
      synchronized (FDBRemoteCRUDGrpc.class) {
        if ((getLoadMetadataMethod = FDBRemoteCRUDGrpc.getLoadMetadataMethod) == null) {
          FDBRemoteCRUDGrpc.getLoadMetadataMethod = getLoadMetadataMethod =
              io.grpc.MethodDescriptor.<cio.fdb.record.grpc.FdbRecordGrpc.FDBLoadMetadataCommand, cio.fdb.record.grpc.FdbRecordGrpc.FDBLoadMetadataResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "LoadMetadata"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  cio.fdb.record.grpc.FdbRecordGrpc.FDBLoadMetadataCommand.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  cio.fdb.record.grpc.FdbRecordGrpc.FDBLoadMetadataResponse.getDefaultInstance()))
              .setSchemaDescriptor(new FDBRemoteCRUDMethodDescriptorSupplier("LoadMetadata"))
              .build();
        }
      }
    }
    return getLoadMetadataMethod;
  }

  private static volatile io.grpc.MethodDescriptor<cio.fdb.record.grpc.FdbRecordGrpc.FDBRegisterSchemaCommand,
      cio.fdb.record.grpc.FdbRecordGrpc.FDBLoadMetadataResponse> getRegisterSchemaMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "RegisterSchema",
      requestType = cio.fdb.record.grpc.FdbRecordGrpc.FDBRegisterSchemaCommand.class,
      responseType = cio.fdb.record.grpc.FdbRecordGrpc.FDBLoadMetadataResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<cio.fdb.record.grpc.FdbRecordGrpc.FDBRegisterSchemaCommand,
      cio.fdb.record.grpc.FdbRecordGrpc.FDBLoadMetadataResponse> getRegisterSchemaMethod() {
    io.grpc.MethodDescriptor<cio.fdb.record.grpc.FdbRecordGrpc.FDBRegisterSchemaCommand, cio.fdb.record.grpc.FdbRecordGrpc.FDBLoadMetadataResponse> getRegisterSchemaMethod;
    if ((getRegisterSchemaMethod = FDBRemoteCRUDGrpc.getRegisterSchemaMethod) == null) {
      synchronized (FDBRemoteCRUDGrpc.class) {
        if ((getRegisterSchemaMethod = FDBRemoteCRUDGrpc.getRegisterSchemaMethod) == null) {
          FDBRemoteCRUDGrpc.getRegisterSchemaMethod = getRegisterSchemaMethod =
              io.grpc.MethodDescriptor.<cio.fdb.record.grpc.FdbRecordGrpc.FDBRegisterSchemaCommand, cio.fdb.record.grpc.FdbRecordGrpc.FDBLoadMetadataResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "RegisterSchema"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  cio.fdb.record.grpc.FdbRecordGrpc.FDBRegisterSchemaCommand.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  cio.fdb.record.grpc.FdbRecordGrpc.FDBLoadMetadataResponse.getDefaultInstance()))
              .setSchemaDescriptor(new FDBRemoteCRUDMethodDescriptorSupplier("RegisterSchema"))
              .build();
        }
      }
    }
    return getRegisterSchemaMethod;
  }

  private static volatile io.grpc.MethodDescriptor<cio.fdb.record.grpc.FdbRecordGrpc.FDBCRUDCommand,
      cio.fdb.record.grpc.FdbRecordGrpc.FDBCRUDResponse> getExecuteMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "execute",
      requestType = cio.fdb.record.grpc.FdbRecordGrpc.FDBCRUDCommand.class,
      responseType = cio.fdb.record.grpc.FdbRecordGrpc.FDBCRUDResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<cio.fdb.record.grpc.FdbRecordGrpc.FDBCRUDCommand,
      cio.fdb.record.grpc.FdbRecordGrpc.FDBCRUDResponse> getExecuteMethod() {
    io.grpc.MethodDescriptor<cio.fdb.record.grpc.FdbRecordGrpc.FDBCRUDCommand, cio.fdb.record.grpc.FdbRecordGrpc.FDBCRUDResponse> getExecuteMethod;
    if ((getExecuteMethod = FDBRemoteCRUDGrpc.getExecuteMethod) == null) {
      synchronized (FDBRemoteCRUDGrpc.class) {
        if ((getExecuteMethod = FDBRemoteCRUDGrpc.getExecuteMethod) == null) {
          FDBRemoteCRUDGrpc.getExecuteMethod = getExecuteMethod =
              io.grpc.MethodDescriptor.<cio.fdb.record.grpc.FdbRecordGrpc.FDBCRUDCommand, cio.fdb.record.grpc.FdbRecordGrpc.FDBCRUDResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "execute"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  cio.fdb.record.grpc.FdbRecordGrpc.FDBCRUDCommand.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  cio.fdb.record.grpc.FdbRecordGrpc.FDBCRUDResponse.getDefaultInstance()))
              .setSchemaDescriptor(new FDBRemoteCRUDMethodDescriptorSupplier("execute"))
              .build();
        }
      }
    }
    return getExecuteMethod;
  }

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static FDBRemoteCRUDStub newStub(io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<FDBRemoteCRUDStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<FDBRemoteCRUDStub>() {
        @java.lang.Override
        public FDBRemoteCRUDStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new FDBRemoteCRUDStub(channel, callOptions);
        }
      };
    return FDBRemoteCRUDStub.newStub(factory, channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static FDBRemoteCRUDBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<FDBRemoteCRUDBlockingStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<FDBRemoteCRUDBlockingStub>() {
        @java.lang.Override
        public FDBRemoteCRUDBlockingStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new FDBRemoteCRUDBlockingStub(channel, callOptions);
        }
      };
    return FDBRemoteCRUDBlockingStub.newStub(factory, channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static FDBRemoteCRUDFutureStub newFutureStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<FDBRemoteCRUDFutureStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<FDBRemoteCRUDFutureStub>() {
        @java.lang.Override
        public FDBRemoteCRUDFutureStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new FDBRemoteCRUDFutureStub(channel, callOptions);
        }
      };
    return FDBRemoteCRUDFutureStub.newStub(factory, channel);
  }

  /**
   */
  public interface AsyncService {

    /**
     */
    default void loadMetadata(cio.fdb.record.grpc.FdbRecordGrpc.FDBLoadMetadataCommand request,
        io.grpc.stub.StreamObserver<cio.fdb.record.grpc.FdbRecordGrpc.FDBLoadMetadataResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getLoadMetadataMethod(), responseObserver);
    }

    /**
     */
    default void registerSchema(cio.fdb.record.grpc.FdbRecordGrpc.FDBRegisterSchemaCommand request,
        io.grpc.stub.StreamObserver<cio.fdb.record.grpc.FdbRecordGrpc.FDBLoadMetadataResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getRegisterSchemaMethod(), responseObserver);
    }

    /**
     */
    default void execute(cio.fdb.record.grpc.FdbRecordGrpc.FDBCRUDCommand request,
        io.grpc.stub.StreamObserver<cio.fdb.record.grpc.FdbRecordGrpc.FDBCRUDResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getExecuteMethod(), responseObserver);
    }
  }

  /**
   * Base class for the server implementation of the service FDBRemoteCRUD.
   */
  public static abstract class FDBRemoteCRUDImplBase
      implements io.grpc.BindableService, AsyncService {

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return FDBRemoteCRUDGrpc.bindService(this);
    }
  }

  /**
   * A stub to allow clients to do asynchronous rpc calls to service FDBRemoteCRUD.
   */
  public static final class FDBRemoteCRUDStub
      extends io.grpc.stub.AbstractAsyncStub<FDBRemoteCRUDStub> {
    private FDBRemoteCRUDStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected FDBRemoteCRUDStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new FDBRemoteCRUDStub(channel, callOptions);
    }

    /**
     */
    public void loadMetadata(cio.fdb.record.grpc.FdbRecordGrpc.FDBLoadMetadataCommand request,
        io.grpc.stub.StreamObserver<cio.fdb.record.grpc.FdbRecordGrpc.FDBLoadMetadataResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getLoadMetadataMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void registerSchema(cio.fdb.record.grpc.FdbRecordGrpc.FDBRegisterSchemaCommand request,
        io.grpc.stub.StreamObserver<cio.fdb.record.grpc.FdbRecordGrpc.FDBLoadMetadataResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getRegisterSchemaMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void execute(cio.fdb.record.grpc.FdbRecordGrpc.FDBCRUDCommand request,
        io.grpc.stub.StreamObserver<cio.fdb.record.grpc.FdbRecordGrpc.FDBCRUDResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getExecuteMethod(), getCallOptions()), request, responseObserver);
    }
  }

  /**
   * A stub to allow clients to do synchronous rpc calls to service FDBRemoteCRUD.
   */
  public static final class FDBRemoteCRUDBlockingStub
      extends io.grpc.stub.AbstractBlockingStub<FDBRemoteCRUDBlockingStub> {
    private FDBRemoteCRUDBlockingStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected FDBRemoteCRUDBlockingStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new FDBRemoteCRUDBlockingStub(channel, callOptions);
    }

    /**
     */
    public cio.fdb.record.grpc.FdbRecordGrpc.FDBLoadMetadataResponse loadMetadata(cio.fdb.record.grpc.FdbRecordGrpc.FDBLoadMetadataCommand request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getLoadMetadataMethod(), getCallOptions(), request);
    }

    /**
     */
    public cio.fdb.record.grpc.FdbRecordGrpc.FDBLoadMetadataResponse registerSchema(cio.fdb.record.grpc.FdbRecordGrpc.FDBRegisterSchemaCommand request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getRegisterSchemaMethod(), getCallOptions(), request);
    }

    /**
     */
    public cio.fdb.record.grpc.FdbRecordGrpc.FDBCRUDResponse execute(cio.fdb.record.grpc.FdbRecordGrpc.FDBCRUDCommand request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getExecuteMethod(), getCallOptions(), request);
    }
  }

  /**
   * A stub to allow clients to do ListenableFuture-style rpc calls to service FDBRemoteCRUD.
   */
  public static final class FDBRemoteCRUDFutureStub
      extends io.grpc.stub.AbstractFutureStub<FDBRemoteCRUDFutureStub> {
    private FDBRemoteCRUDFutureStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected FDBRemoteCRUDFutureStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new FDBRemoteCRUDFutureStub(channel, callOptions);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<cio.fdb.record.grpc.FdbRecordGrpc.FDBLoadMetadataResponse> loadMetadata(
        cio.fdb.record.grpc.FdbRecordGrpc.FDBLoadMetadataCommand request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getLoadMetadataMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<cio.fdb.record.grpc.FdbRecordGrpc.FDBLoadMetadataResponse> registerSchema(
        cio.fdb.record.grpc.FdbRecordGrpc.FDBRegisterSchemaCommand request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getRegisterSchemaMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<cio.fdb.record.grpc.FdbRecordGrpc.FDBCRUDResponse> execute(
        cio.fdb.record.grpc.FdbRecordGrpc.FDBCRUDCommand request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getExecuteMethod(), getCallOptions()), request);
    }
  }

  private static final int METHODID_LOAD_METADATA = 0;
  private static final int METHODID_REGISTER_SCHEMA = 1;
  private static final int METHODID_EXECUTE = 2;

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
        case METHODID_LOAD_METADATA:
          serviceImpl.loadMetadata((cio.fdb.record.grpc.FdbRecordGrpc.FDBLoadMetadataCommand) request,
              (io.grpc.stub.StreamObserver<cio.fdb.record.grpc.FdbRecordGrpc.FDBLoadMetadataResponse>) responseObserver);
          break;
        case METHODID_REGISTER_SCHEMA:
          serviceImpl.registerSchema((cio.fdb.record.grpc.FdbRecordGrpc.FDBRegisterSchemaCommand) request,
              (io.grpc.stub.StreamObserver<cio.fdb.record.grpc.FdbRecordGrpc.FDBLoadMetadataResponse>) responseObserver);
          break;
        case METHODID_EXECUTE:
          serviceImpl.execute((cio.fdb.record.grpc.FdbRecordGrpc.FDBCRUDCommand) request,
              (io.grpc.stub.StreamObserver<cio.fdb.record.grpc.FdbRecordGrpc.FDBCRUDResponse>) responseObserver);
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
          getLoadMetadataMethod(),
          io.grpc.stub.ServerCalls.asyncUnaryCall(
            new MethodHandlers<
              cio.fdb.record.grpc.FdbRecordGrpc.FDBLoadMetadataCommand,
              cio.fdb.record.grpc.FdbRecordGrpc.FDBLoadMetadataResponse>(
                service, METHODID_LOAD_METADATA)))
        .addMethod(
          getRegisterSchemaMethod(),
          io.grpc.stub.ServerCalls.asyncUnaryCall(
            new MethodHandlers<
              cio.fdb.record.grpc.FdbRecordGrpc.FDBRegisterSchemaCommand,
              cio.fdb.record.grpc.FdbRecordGrpc.FDBLoadMetadataResponse>(
                service, METHODID_REGISTER_SCHEMA)))
        .addMethod(
          getExecuteMethod(),
          io.grpc.stub.ServerCalls.asyncUnaryCall(
            new MethodHandlers<
              cio.fdb.record.grpc.FdbRecordGrpc.FDBCRUDCommand,
              cio.fdb.record.grpc.FdbRecordGrpc.FDBCRUDResponse>(
                service, METHODID_EXECUTE)))
        .build();
  }

  private static abstract class FDBRemoteCRUDBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoFileDescriptorSupplier, io.grpc.protobuf.ProtoServiceDescriptorSupplier {
    FDBRemoteCRUDBaseDescriptorSupplier() {}

    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return cio.fdb.record.grpc.FdbRecordGrpc.getDescriptor();
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.ServiceDescriptor getServiceDescriptor() {
      return getFileDescriptor().findServiceByName("FDBRemoteCRUD");
    }
  }

  private static final class FDBRemoteCRUDFileDescriptorSupplier
      extends FDBRemoteCRUDBaseDescriptorSupplier {
    FDBRemoteCRUDFileDescriptorSupplier() {}
  }

  private static final class FDBRemoteCRUDMethodDescriptorSupplier
      extends FDBRemoteCRUDBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoMethodDescriptorSupplier {
    private final java.lang.String methodName;

    FDBRemoteCRUDMethodDescriptorSupplier(java.lang.String methodName) {
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
      synchronized (FDBRemoteCRUDGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new FDBRemoteCRUDFileDescriptorSupplier())
              .addMethod(getLoadMetadataMethod())
              .addMethod(getRegisterSchemaMethod())
              .addMethod(getExecuteMethod())
              .build();
        }
      }
    }
    return result;
  }
}
