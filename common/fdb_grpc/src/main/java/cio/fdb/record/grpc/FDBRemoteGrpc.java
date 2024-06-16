package cio.fdb.record.grpc;

import static io.grpc.MethodDescriptor.generateFullMethodName;

/**
 */
@javax.annotation.Generated(
    value = "by gRPC proto compiler (version 1.64.0)",
    comments = "Source: fdb_record_grpc.proto")
@io.grpc.stub.annotations.GrpcGenerated
public final class FDBRemoteGrpc {

  private FDBRemoteGrpc() {}

  public static final java.lang.String SERVICE_NAME = "cio.fdb.record.grpc.FDBRemote";

  // Static method descriptors that strictly reflect the proto.
  private static volatile io.grpc.MethodDescriptor<cio.fdb.record.grpc.FdbRecordGrpc.FDBRemoteSessionRequest,
      cio.fdb.record.grpc.FdbRecordGrpc.FDBRemoteSessionHandle> getNewSessionMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "NewSession",
      requestType = cio.fdb.record.grpc.FdbRecordGrpc.FDBRemoteSessionRequest.class,
      responseType = cio.fdb.record.grpc.FdbRecordGrpc.FDBRemoteSessionHandle.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<cio.fdb.record.grpc.FdbRecordGrpc.FDBRemoteSessionRequest,
      cio.fdb.record.grpc.FdbRecordGrpc.FDBRemoteSessionHandle> getNewSessionMethod() {
    io.grpc.MethodDescriptor<cio.fdb.record.grpc.FdbRecordGrpc.FDBRemoteSessionRequest, cio.fdb.record.grpc.FdbRecordGrpc.FDBRemoteSessionHandle> getNewSessionMethod;
    if ((getNewSessionMethod = FDBRemoteGrpc.getNewSessionMethod) == null) {
      synchronized (FDBRemoteGrpc.class) {
        if ((getNewSessionMethod = FDBRemoteGrpc.getNewSessionMethod) == null) {
          FDBRemoteGrpc.getNewSessionMethod = getNewSessionMethod =
              io.grpc.MethodDescriptor.<cio.fdb.record.grpc.FdbRecordGrpc.FDBRemoteSessionRequest, cio.fdb.record.grpc.FdbRecordGrpc.FDBRemoteSessionHandle>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "NewSession"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  cio.fdb.record.grpc.FdbRecordGrpc.FDBRemoteSessionRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  cio.fdb.record.grpc.FdbRecordGrpc.FDBRemoteSessionHandle.getDefaultInstance()))
              .setSchemaDescriptor(new FDBRemoteMethodDescriptorSupplier("NewSession"))
              .build();
        }
      }
    }
    return getNewSessionMethod;
  }

  private static volatile io.grpc.MethodDescriptor<cio.fdb.record.grpc.FdbRecordGrpc.FDBRemoteRecordStoreRequest,
      cio.fdb.record.grpc.FdbRecordGrpc.FDBRemoteRecordStoreHandle> getNewRecordStoreMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "NewRecordStore",
      requestType = cio.fdb.record.grpc.FdbRecordGrpc.FDBRemoteRecordStoreRequest.class,
      responseType = cio.fdb.record.grpc.FdbRecordGrpc.FDBRemoteRecordStoreHandle.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<cio.fdb.record.grpc.FdbRecordGrpc.FDBRemoteRecordStoreRequest,
      cio.fdb.record.grpc.FdbRecordGrpc.FDBRemoteRecordStoreHandle> getNewRecordStoreMethod() {
    io.grpc.MethodDescriptor<cio.fdb.record.grpc.FdbRecordGrpc.FDBRemoteRecordStoreRequest, cio.fdb.record.grpc.FdbRecordGrpc.FDBRemoteRecordStoreHandle> getNewRecordStoreMethod;
    if ((getNewRecordStoreMethod = FDBRemoteGrpc.getNewRecordStoreMethod) == null) {
      synchronized (FDBRemoteGrpc.class) {
        if ((getNewRecordStoreMethod = FDBRemoteGrpc.getNewRecordStoreMethod) == null) {
          FDBRemoteGrpc.getNewRecordStoreMethod = getNewRecordStoreMethod =
              io.grpc.MethodDescriptor.<cio.fdb.record.grpc.FdbRecordGrpc.FDBRemoteRecordStoreRequest, cio.fdb.record.grpc.FdbRecordGrpc.FDBRemoteRecordStoreHandle>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "NewRecordStore"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  cio.fdb.record.grpc.FdbRecordGrpc.FDBRemoteRecordStoreRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  cio.fdb.record.grpc.FdbRecordGrpc.FDBRemoteRecordStoreHandle.getDefaultInstance()))
              .setSchemaDescriptor(new FDBRemoteMethodDescriptorSupplier("NewRecordStore"))
              .build();
        }
      }
    }
    return getNewRecordStoreMethod;
  }

  private static volatile io.grpc.MethodDescriptor<cio.fdb.record.grpc.FdbRecordGrpc.FDBRemoteSessionCommitRequest,
      cio.fdb.record.grpc.FdbRecordGrpc.FDBRemoteSessionCommitResponse> getCommitMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "commit",
      requestType = cio.fdb.record.grpc.FdbRecordGrpc.FDBRemoteSessionCommitRequest.class,
      responseType = cio.fdb.record.grpc.FdbRecordGrpc.FDBRemoteSessionCommitResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<cio.fdb.record.grpc.FdbRecordGrpc.FDBRemoteSessionCommitRequest,
      cio.fdb.record.grpc.FdbRecordGrpc.FDBRemoteSessionCommitResponse> getCommitMethod() {
    io.grpc.MethodDescriptor<cio.fdb.record.grpc.FdbRecordGrpc.FDBRemoteSessionCommitRequest, cio.fdb.record.grpc.FdbRecordGrpc.FDBRemoteSessionCommitResponse> getCommitMethod;
    if ((getCommitMethod = FDBRemoteGrpc.getCommitMethod) == null) {
      synchronized (FDBRemoteGrpc.class) {
        if ((getCommitMethod = FDBRemoteGrpc.getCommitMethod) == null) {
          FDBRemoteGrpc.getCommitMethod = getCommitMethod =
              io.grpc.MethodDescriptor.<cio.fdb.record.grpc.FdbRecordGrpc.FDBRemoteSessionCommitRequest, cio.fdb.record.grpc.FdbRecordGrpc.FDBRemoteSessionCommitResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "commit"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  cio.fdb.record.grpc.FdbRecordGrpc.FDBRemoteSessionCommitRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  cio.fdb.record.grpc.FdbRecordGrpc.FDBRemoteSessionCommitResponse.getDefaultInstance()))
              .setSchemaDescriptor(new FDBRemoteMethodDescriptorSupplier("commit"))
              .build();
        }
      }
    }
    return getCommitMethod;
  }

  private static volatile io.grpc.MethodDescriptor<cio.fdb.record.grpc.FdbRecordGrpc.FDBSaveRecordCommand,
      cio.fdb.record.grpc.FdbRecordGrpc.FDBSaveRecordResult> getSaveRecordMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "SaveRecord",
      requestType = cio.fdb.record.grpc.FdbRecordGrpc.FDBSaveRecordCommand.class,
      responseType = cio.fdb.record.grpc.FdbRecordGrpc.FDBSaveRecordResult.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<cio.fdb.record.grpc.FdbRecordGrpc.FDBSaveRecordCommand,
      cio.fdb.record.grpc.FdbRecordGrpc.FDBSaveRecordResult> getSaveRecordMethod() {
    io.grpc.MethodDescriptor<cio.fdb.record.grpc.FdbRecordGrpc.FDBSaveRecordCommand, cio.fdb.record.grpc.FdbRecordGrpc.FDBSaveRecordResult> getSaveRecordMethod;
    if ((getSaveRecordMethod = FDBRemoteGrpc.getSaveRecordMethod) == null) {
      synchronized (FDBRemoteGrpc.class) {
        if ((getSaveRecordMethod = FDBRemoteGrpc.getSaveRecordMethod) == null) {
          FDBRemoteGrpc.getSaveRecordMethod = getSaveRecordMethod =
              io.grpc.MethodDescriptor.<cio.fdb.record.grpc.FdbRecordGrpc.FDBSaveRecordCommand, cio.fdb.record.grpc.FdbRecordGrpc.FDBSaveRecordResult>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "SaveRecord"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  cio.fdb.record.grpc.FdbRecordGrpc.FDBSaveRecordCommand.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  cio.fdb.record.grpc.FdbRecordGrpc.FDBSaveRecordResult.getDefaultInstance()))
              .setSchemaDescriptor(new FDBRemoteMethodDescriptorSupplier("SaveRecord"))
              .build();
        }
      }
    }
    return getSaveRecordMethod;
  }

  private static volatile io.grpc.MethodDescriptor<cio.fdb.record.grpc.FdbRecordGrpc.FDBDeleteRecordCommand,
      cio.fdb.record.grpc.FdbRecordGrpc.FDBDeleteRecordResult> getDeleteRecordMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "DeleteRecord",
      requestType = cio.fdb.record.grpc.FdbRecordGrpc.FDBDeleteRecordCommand.class,
      responseType = cio.fdb.record.grpc.FdbRecordGrpc.FDBDeleteRecordResult.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<cio.fdb.record.grpc.FdbRecordGrpc.FDBDeleteRecordCommand,
      cio.fdb.record.grpc.FdbRecordGrpc.FDBDeleteRecordResult> getDeleteRecordMethod() {
    io.grpc.MethodDescriptor<cio.fdb.record.grpc.FdbRecordGrpc.FDBDeleteRecordCommand, cio.fdb.record.grpc.FdbRecordGrpc.FDBDeleteRecordResult> getDeleteRecordMethod;
    if ((getDeleteRecordMethod = FDBRemoteGrpc.getDeleteRecordMethod) == null) {
      synchronized (FDBRemoteGrpc.class) {
        if ((getDeleteRecordMethod = FDBRemoteGrpc.getDeleteRecordMethod) == null) {
          FDBRemoteGrpc.getDeleteRecordMethod = getDeleteRecordMethod =
              io.grpc.MethodDescriptor.<cio.fdb.record.grpc.FdbRecordGrpc.FDBDeleteRecordCommand, cio.fdb.record.grpc.FdbRecordGrpc.FDBDeleteRecordResult>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "DeleteRecord"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  cio.fdb.record.grpc.FdbRecordGrpc.FDBDeleteRecordCommand.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  cio.fdb.record.grpc.FdbRecordGrpc.FDBDeleteRecordResult.getDefaultInstance()))
              .setSchemaDescriptor(new FDBRemoteMethodDescriptorSupplier("DeleteRecord"))
              .build();
        }
      }
    }
    return getDeleteRecordMethod;
  }

  private static volatile io.grpc.MethodDescriptor<cio.fdb.record.grpc.FdbRecordGrpc.FDBDumpAllCommand,
      cio.fdb.record.grpc.FdbRecordGrpc.FDBDumpAllResponseBatch> getGetDumpMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "GetDump",
      requestType = cio.fdb.record.grpc.FdbRecordGrpc.FDBDumpAllCommand.class,
      responseType = cio.fdb.record.grpc.FdbRecordGrpc.FDBDumpAllResponseBatch.class,
      methodType = io.grpc.MethodDescriptor.MethodType.SERVER_STREAMING)
  public static io.grpc.MethodDescriptor<cio.fdb.record.grpc.FdbRecordGrpc.FDBDumpAllCommand,
      cio.fdb.record.grpc.FdbRecordGrpc.FDBDumpAllResponseBatch> getGetDumpMethod() {
    io.grpc.MethodDescriptor<cio.fdb.record.grpc.FdbRecordGrpc.FDBDumpAllCommand, cio.fdb.record.grpc.FdbRecordGrpc.FDBDumpAllResponseBatch> getGetDumpMethod;
    if ((getGetDumpMethod = FDBRemoteGrpc.getGetDumpMethod) == null) {
      synchronized (FDBRemoteGrpc.class) {
        if ((getGetDumpMethod = FDBRemoteGrpc.getGetDumpMethod) == null) {
          FDBRemoteGrpc.getGetDumpMethod = getGetDumpMethod =
              io.grpc.MethodDescriptor.<cio.fdb.record.grpc.FdbRecordGrpc.FDBDumpAllCommand, cio.fdb.record.grpc.FdbRecordGrpc.FDBDumpAllResponseBatch>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.SERVER_STREAMING)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "GetDump"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  cio.fdb.record.grpc.FdbRecordGrpc.FDBDumpAllCommand.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  cio.fdb.record.grpc.FdbRecordGrpc.FDBDumpAllResponseBatch.getDefaultInstance()))
              .setSchemaDescriptor(new FDBRemoteMethodDescriptorSupplier("GetDump"))
              .build();
        }
      }
    }
    return getGetDumpMethod;
  }

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static FDBRemoteStub newStub(io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<FDBRemoteStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<FDBRemoteStub>() {
        @java.lang.Override
        public FDBRemoteStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new FDBRemoteStub(channel, callOptions);
        }
      };
    return FDBRemoteStub.newStub(factory, channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static FDBRemoteBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<FDBRemoteBlockingStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<FDBRemoteBlockingStub>() {
        @java.lang.Override
        public FDBRemoteBlockingStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new FDBRemoteBlockingStub(channel, callOptions);
        }
      };
    return FDBRemoteBlockingStub.newStub(factory, channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static FDBRemoteFutureStub newFutureStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<FDBRemoteFutureStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<FDBRemoteFutureStub>() {
        @java.lang.Override
        public FDBRemoteFutureStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new FDBRemoteFutureStub(channel, callOptions);
        }
      };
    return FDBRemoteFutureStub.newStub(factory, channel);
  }

  /**
   */
  public interface AsyncService {

    /**
     */
    default void newSession(cio.fdb.record.grpc.FdbRecordGrpc.FDBRemoteSessionRequest request,
        io.grpc.stub.StreamObserver<cio.fdb.record.grpc.FdbRecordGrpc.FDBRemoteSessionHandle> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getNewSessionMethod(), responseObserver);
    }

    /**
     */
    default void newRecordStore(cio.fdb.record.grpc.FdbRecordGrpc.FDBRemoteRecordStoreRequest request,
        io.grpc.stub.StreamObserver<cio.fdb.record.grpc.FdbRecordGrpc.FDBRemoteRecordStoreHandle> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getNewRecordStoreMethod(), responseObserver);
    }

    /**
     */
    default void commit(cio.fdb.record.grpc.FdbRecordGrpc.FDBRemoteSessionCommitRequest request,
        io.grpc.stub.StreamObserver<cio.fdb.record.grpc.FdbRecordGrpc.FDBRemoteSessionCommitResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getCommitMethod(), responseObserver);
    }

    /**
     */
    default void saveRecord(cio.fdb.record.grpc.FdbRecordGrpc.FDBSaveRecordCommand request,
        io.grpc.stub.StreamObserver<cio.fdb.record.grpc.FdbRecordGrpc.FDBSaveRecordResult> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getSaveRecordMethod(), responseObserver);
    }

    /**
     */
    default void deleteRecord(cio.fdb.record.grpc.FdbRecordGrpc.FDBDeleteRecordCommand request,
        io.grpc.stub.StreamObserver<cio.fdb.record.grpc.FdbRecordGrpc.FDBDeleteRecordResult> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getDeleteRecordMethod(), responseObserver);
    }

    /**
     */
    default void getDump(cio.fdb.record.grpc.FdbRecordGrpc.FDBDumpAllCommand request,
        io.grpc.stub.StreamObserver<cio.fdb.record.grpc.FdbRecordGrpc.FDBDumpAllResponseBatch> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getGetDumpMethod(), responseObserver);
    }
  }

  /**
   * Base class for the server implementation of the service FDBRemote.
   */
  public static abstract class FDBRemoteImplBase
      implements io.grpc.BindableService, AsyncService {

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return FDBRemoteGrpc.bindService(this);
    }
  }

  /**
   * A stub to allow clients to do asynchronous rpc calls to service FDBRemote.
   */
  public static final class FDBRemoteStub
      extends io.grpc.stub.AbstractAsyncStub<FDBRemoteStub> {
    private FDBRemoteStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected FDBRemoteStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new FDBRemoteStub(channel, callOptions);
    }

    /**
     */
    public void newSession(cio.fdb.record.grpc.FdbRecordGrpc.FDBRemoteSessionRequest request,
        io.grpc.stub.StreamObserver<cio.fdb.record.grpc.FdbRecordGrpc.FDBRemoteSessionHandle> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getNewSessionMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void newRecordStore(cio.fdb.record.grpc.FdbRecordGrpc.FDBRemoteRecordStoreRequest request,
        io.grpc.stub.StreamObserver<cio.fdb.record.grpc.FdbRecordGrpc.FDBRemoteRecordStoreHandle> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getNewRecordStoreMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void commit(cio.fdb.record.grpc.FdbRecordGrpc.FDBRemoteSessionCommitRequest request,
        io.grpc.stub.StreamObserver<cio.fdb.record.grpc.FdbRecordGrpc.FDBRemoteSessionCommitResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getCommitMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void saveRecord(cio.fdb.record.grpc.FdbRecordGrpc.FDBSaveRecordCommand request,
        io.grpc.stub.StreamObserver<cio.fdb.record.grpc.FdbRecordGrpc.FDBSaveRecordResult> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getSaveRecordMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void deleteRecord(cio.fdb.record.grpc.FdbRecordGrpc.FDBDeleteRecordCommand request,
        io.grpc.stub.StreamObserver<cio.fdb.record.grpc.FdbRecordGrpc.FDBDeleteRecordResult> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getDeleteRecordMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void getDump(cio.fdb.record.grpc.FdbRecordGrpc.FDBDumpAllCommand request,
        io.grpc.stub.StreamObserver<cio.fdb.record.grpc.FdbRecordGrpc.FDBDumpAllResponseBatch> responseObserver) {
      io.grpc.stub.ClientCalls.asyncServerStreamingCall(
          getChannel().newCall(getGetDumpMethod(), getCallOptions()), request, responseObserver);
    }
  }

  /**
   * A stub to allow clients to do synchronous rpc calls to service FDBRemote.
   */
  public static final class FDBRemoteBlockingStub
      extends io.grpc.stub.AbstractBlockingStub<FDBRemoteBlockingStub> {
    private FDBRemoteBlockingStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected FDBRemoteBlockingStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new FDBRemoteBlockingStub(channel, callOptions);
    }

    /**
     */
    public cio.fdb.record.grpc.FdbRecordGrpc.FDBRemoteSessionHandle newSession(cio.fdb.record.grpc.FdbRecordGrpc.FDBRemoteSessionRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getNewSessionMethod(), getCallOptions(), request);
    }

    /**
     */
    public cio.fdb.record.grpc.FdbRecordGrpc.FDBRemoteRecordStoreHandle newRecordStore(cio.fdb.record.grpc.FdbRecordGrpc.FDBRemoteRecordStoreRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getNewRecordStoreMethod(), getCallOptions(), request);
    }

    /**
     */
    public cio.fdb.record.grpc.FdbRecordGrpc.FDBRemoteSessionCommitResponse commit(cio.fdb.record.grpc.FdbRecordGrpc.FDBRemoteSessionCommitRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getCommitMethod(), getCallOptions(), request);
    }

    /**
     */
    public cio.fdb.record.grpc.FdbRecordGrpc.FDBSaveRecordResult saveRecord(cio.fdb.record.grpc.FdbRecordGrpc.FDBSaveRecordCommand request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getSaveRecordMethod(), getCallOptions(), request);
    }

    /**
     */
    public cio.fdb.record.grpc.FdbRecordGrpc.FDBDeleteRecordResult deleteRecord(cio.fdb.record.grpc.FdbRecordGrpc.FDBDeleteRecordCommand request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getDeleteRecordMethod(), getCallOptions(), request);
    }

    /**
     */
    public java.util.Iterator<cio.fdb.record.grpc.FdbRecordGrpc.FDBDumpAllResponseBatch> getDump(
        cio.fdb.record.grpc.FdbRecordGrpc.FDBDumpAllCommand request) {
      return io.grpc.stub.ClientCalls.blockingServerStreamingCall(
          getChannel(), getGetDumpMethod(), getCallOptions(), request);
    }
  }

  /**
   * A stub to allow clients to do ListenableFuture-style rpc calls to service FDBRemote.
   */
  public static final class FDBRemoteFutureStub
      extends io.grpc.stub.AbstractFutureStub<FDBRemoteFutureStub> {
    private FDBRemoteFutureStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected FDBRemoteFutureStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new FDBRemoteFutureStub(channel, callOptions);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<cio.fdb.record.grpc.FdbRecordGrpc.FDBRemoteSessionHandle> newSession(
        cio.fdb.record.grpc.FdbRecordGrpc.FDBRemoteSessionRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getNewSessionMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<cio.fdb.record.grpc.FdbRecordGrpc.FDBRemoteRecordStoreHandle> newRecordStore(
        cio.fdb.record.grpc.FdbRecordGrpc.FDBRemoteRecordStoreRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getNewRecordStoreMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<cio.fdb.record.grpc.FdbRecordGrpc.FDBRemoteSessionCommitResponse> commit(
        cio.fdb.record.grpc.FdbRecordGrpc.FDBRemoteSessionCommitRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getCommitMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<cio.fdb.record.grpc.FdbRecordGrpc.FDBSaveRecordResult> saveRecord(
        cio.fdb.record.grpc.FdbRecordGrpc.FDBSaveRecordCommand request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getSaveRecordMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<cio.fdb.record.grpc.FdbRecordGrpc.FDBDeleteRecordResult> deleteRecord(
        cio.fdb.record.grpc.FdbRecordGrpc.FDBDeleteRecordCommand request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getDeleteRecordMethod(), getCallOptions()), request);
    }
  }

  private static final int METHODID_NEW_SESSION = 0;
  private static final int METHODID_NEW_RECORD_STORE = 1;
  private static final int METHODID_COMMIT = 2;
  private static final int METHODID_SAVE_RECORD = 3;
  private static final int METHODID_DELETE_RECORD = 4;
  private static final int METHODID_GET_DUMP = 5;

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
        case METHODID_NEW_SESSION:
          serviceImpl.newSession((cio.fdb.record.grpc.FdbRecordGrpc.FDBRemoteSessionRequest) request,
              (io.grpc.stub.StreamObserver<cio.fdb.record.grpc.FdbRecordGrpc.FDBRemoteSessionHandle>) responseObserver);
          break;
        case METHODID_NEW_RECORD_STORE:
          serviceImpl.newRecordStore((cio.fdb.record.grpc.FdbRecordGrpc.FDBRemoteRecordStoreRequest) request,
              (io.grpc.stub.StreamObserver<cio.fdb.record.grpc.FdbRecordGrpc.FDBRemoteRecordStoreHandle>) responseObserver);
          break;
        case METHODID_COMMIT:
          serviceImpl.commit((cio.fdb.record.grpc.FdbRecordGrpc.FDBRemoteSessionCommitRequest) request,
              (io.grpc.stub.StreamObserver<cio.fdb.record.grpc.FdbRecordGrpc.FDBRemoteSessionCommitResponse>) responseObserver);
          break;
        case METHODID_SAVE_RECORD:
          serviceImpl.saveRecord((cio.fdb.record.grpc.FdbRecordGrpc.FDBSaveRecordCommand) request,
              (io.grpc.stub.StreamObserver<cio.fdb.record.grpc.FdbRecordGrpc.FDBSaveRecordResult>) responseObserver);
          break;
        case METHODID_DELETE_RECORD:
          serviceImpl.deleteRecord((cio.fdb.record.grpc.FdbRecordGrpc.FDBDeleteRecordCommand) request,
              (io.grpc.stub.StreamObserver<cio.fdb.record.grpc.FdbRecordGrpc.FDBDeleteRecordResult>) responseObserver);
          break;
        case METHODID_GET_DUMP:
          serviceImpl.getDump((cio.fdb.record.grpc.FdbRecordGrpc.FDBDumpAllCommand) request,
              (io.grpc.stub.StreamObserver<cio.fdb.record.grpc.FdbRecordGrpc.FDBDumpAllResponseBatch>) responseObserver);
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
          getNewSessionMethod(),
          io.grpc.stub.ServerCalls.asyncUnaryCall(
            new MethodHandlers<
              cio.fdb.record.grpc.FdbRecordGrpc.FDBRemoteSessionRequest,
              cio.fdb.record.grpc.FdbRecordGrpc.FDBRemoteSessionHandle>(
                service, METHODID_NEW_SESSION)))
        .addMethod(
          getNewRecordStoreMethod(),
          io.grpc.stub.ServerCalls.asyncUnaryCall(
            new MethodHandlers<
              cio.fdb.record.grpc.FdbRecordGrpc.FDBRemoteRecordStoreRequest,
              cio.fdb.record.grpc.FdbRecordGrpc.FDBRemoteRecordStoreHandle>(
                service, METHODID_NEW_RECORD_STORE)))
        .addMethod(
          getCommitMethod(),
          io.grpc.stub.ServerCalls.asyncUnaryCall(
            new MethodHandlers<
              cio.fdb.record.grpc.FdbRecordGrpc.FDBRemoteSessionCommitRequest,
              cio.fdb.record.grpc.FdbRecordGrpc.FDBRemoteSessionCommitResponse>(
                service, METHODID_COMMIT)))
        .addMethod(
          getSaveRecordMethod(),
          io.grpc.stub.ServerCalls.asyncUnaryCall(
            new MethodHandlers<
              cio.fdb.record.grpc.FdbRecordGrpc.FDBSaveRecordCommand,
              cio.fdb.record.grpc.FdbRecordGrpc.FDBSaveRecordResult>(
                service, METHODID_SAVE_RECORD)))
        .addMethod(
          getDeleteRecordMethod(),
          io.grpc.stub.ServerCalls.asyncUnaryCall(
            new MethodHandlers<
              cio.fdb.record.grpc.FdbRecordGrpc.FDBDeleteRecordCommand,
              cio.fdb.record.grpc.FdbRecordGrpc.FDBDeleteRecordResult>(
                service, METHODID_DELETE_RECORD)))
        .addMethod(
          getGetDumpMethod(),
          io.grpc.stub.ServerCalls.asyncServerStreamingCall(
            new MethodHandlers<
              cio.fdb.record.grpc.FdbRecordGrpc.FDBDumpAllCommand,
              cio.fdb.record.grpc.FdbRecordGrpc.FDBDumpAllResponseBatch>(
                service, METHODID_GET_DUMP)))
        .build();
  }

  private static abstract class FDBRemoteBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoFileDescriptorSupplier, io.grpc.protobuf.ProtoServiceDescriptorSupplier {
    FDBRemoteBaseDescriptorSupplier() {}

    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return cio.fdb.record.grpc.FdbRecordGrpc.getDescriptor();
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.ServiceDescriptor getServiceDescriptor() {
      return getFileDescriptor().findServiceByName("FDBRemote");
    }
  }

  private static final class FDBRemoteFileDescriptorSupplier
      extends FDBRemoteBaseDescriptorSupplier {
    FDBRemoteFileDescriptorSupplier() {}
  }

  private static final class FDBRemoteMethodDescriptorSupplier
      extends FDBRemoteBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoMethodDescriptorSupplier {
    private final java.lang.String methodName;

    FDBRemoteMethodDescriptorSupplier(java.lang.String methodName) {
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
      synchronized (FDBRemoteGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new FDBRemoteFileDescriptorSupplier())
              .addMethod(getNewSessionMethod())
              .addMethod(getNewRecordStoreMethod())
              .addMethod(getCommitMethod())
              .addMethod(getSaveRecordMethod())
              .addMethod(getDeleteRecordMethod())
              .addMethod(getGetDumpMethod())
              .build();
        }
      }
    }
    return result;
  }
}
