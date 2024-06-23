package io.fbd.record.rpc

import com.google.protobuf.ByteString
import io.fdb.record.grpc.StreamingSession.{StreamingSessionRequest, StreamingSessionResponse}
import io.grpc.stub.StreamObserver
import org.slf4j.LoggerFactory

import java.util.concurrent.{ConcurrentHashMap, Executor, Executors, TimeUnit}
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration.Duration
import scala.concurrent.{ExecutionContext, Future}
import scala.util.{Failure, Success, Try}


object StreamingSessionRaw{
  implicit object ExtractIdRequest extends ExtractId[StreamingSessionRequest]{
    override def extract(command: StreamingSessionRequest ): Long = command.getId
  }
  implicit object FromThrowableResponse extends ObjBuilder[StreamingSessionResponse]{
    override def build( id: Long, ex: Throwable ): StreamingSessionResponse = {
      StreamingSessionResponse.newBuilder()
        .setId( id)
        .setError( ByteString.copyFromUtf8(ex.toString) )
        .build()
    }
  }

  object EchoHandler extends StreamingSessionHandler[StreamingSessionRequest, StreamingSessionResponse] {
    override def handle(value: StreamingSessionRequest): Future[StreamingSessionResponse] = {
      Future.successful(
        StreamingSessionResponse.newBuilder()
          .setId( value.getId)
          .setError( value.getCommand )
          .build()
      )
    }
  }

  def ECHO( responseObserver: StreamObserver[StreamingSessionResponse]):
  StreamingSession[StreamingSessionRequest, StreamingSessionResponse] = {
    new StreamingSession[StreamingSessionRequest, StreamingSessionResponse](
      responseObserver,
      EchoHandler,
      Duration.apply(3, TimeUnit.SECONDS)
    )
  }

}

trait StreamingSessionHandler[Rq, Rs]{
  def handle( value:  Rq ): Future[Rs]
}

trait ExtractId[R]{
  def extract( from: R ): Long
}
trait ObjBuilder[R]{
  def build( id:Long, ex: Throwable ): R
}
class ConcurrentStreamObserver[R]( inner: StreamObserver[R]) extends StreamObserver[R]{

  override def onNext(value: R): Unit = {
    this.synchronized{
      inner.onNext(value)
    }
  }

  override def onError(t: Throwable): Unit = {
    this.synchronized( inner.onError(t))
  }

  override def onCompleted(): Unit = {
    this.synchronized(inner.onCompleted())
  }
}
class StreamingSession[Rq: ExtractId , Rs: ObjBuilder](
                        clientStream: StreamObserver[Rs],
                        handler: StreamingSessionHandler[Rq, Rs],
                        timeout: Duration
                      ) extends StreamObserver[Rq]{

  private val logger = LoggerFactory.getLogger( this.getClass )
  private val responseObserver = new ConcurrentStreamObserver[Rs]( clientStream)
  private val taskFutures = new ConcurrentHashMap[Long, Future[_]]()

  override def onNext(command: Rq): Unit = {
    val reqId = implicitly[ExtractId[Rq]].extract(command)
    logger.info(s" Received request with ID:$reqId command: $command")
    val taskF = handler.handle(command)
    taskFutures.put( reqId, taskF)
    taskF.onComplete {
      case Failure(ex) =>
        taskFutures.remove(reqId)
        handleError( reqId, ex )
        logger.error(s" Failed request with ID:$reqId command:", ex)
      case Success(resp) =>
        taskFutures.remove(reqId)
        responseObserver.onNext( resp )
        logger.info(s" Sent request with ID:$reqId command: $resp")
    }
  }
  private def handleError( commandId: Long , ex: Throwable ): Unit = {
    val response = implicitly[ObjBuilder[Rs]].build( commandId, ex )
    responseObserver.onNext( response )
    logger.error(s" Error processing request with ID:$commandId ERROR: $response")
  }

  override def onError(t: Throwable): Unit = {
    logger.error(" Error from client while streaming session ", t)
    responseObserver.onError(t)
  }

  private def   awaitFinish( timeoutMillis: Long, ex: Executor ): Future[_] = {
    Future{
      val now = System.currentTimeMillis()
      logger.info(s"Waiting for all requests to be processed. remaining ${taskFutures.size()} ")
      while( taskFutures.size() > 0 && ( System.currentTimeMillis() - now) < timeoutMillis ){
        Try{
          Thread.sleep(taskFutures.size()* 10)
        }
      }
      logger.info(s"Finished Waiting for all requests to be processed.")
    }(ExecutionContext.fromExecutor(ex))
  }

  override def onCompleted(): Unit = {
    logger.info(" Session streaming client finished.")
    val ex = Executors.newSingleThreadExecutor()
    awaitFinish( timeout.toMillis, ex ).andThen {
      case Failure(t) =>
        responseObserver.onError(t)
        logger.error(" Error waiting for ongoing requests from client", t)
      case Success(_) =>
        responseObserver.onCompleted()
        logger.info(" Finished streaming session  ")
    }
    ex.shutdown()
  }
}
