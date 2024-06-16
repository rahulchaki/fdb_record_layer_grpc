package cio.fbd.record.grpc.server

import cio.fbd.util.FDBUtil
import com.apple.foundationdb.record.provider.foundationdb._
import com.apple.foundationdb.record.provider.foundationdb.keyspace._
import com.apple.foundationdb.record.{RecordMetaData, RecordMetaDataOptionsProto, RecordMetaDataProto}
import com.apple.foundationdb.tuple.Tuple
import cio.fdb.record.grpc.FdbRecordGrpc.FDBDirectory
import com.google.protobuf.DescriptorProtos.FileDescriptorProto
import com.google.protobuf.Descriptors.FileDescriptor
import com.google.protobuf.{ByteString, DynamicMessage, ExtensionRegistry, Message}

import java.util.concurrent.ConcurrentHashMap
import scala.annotation.tailrec
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.{Future, Promise}
import scala.jdk.FutureConverters._
import scala.util.{Failure, Random, Success}


class RemoteFDBSessionManager(fdb: FDBDatabase) {

  private val extensionRegistry = ExtensionRegistry.newInstance()
  RecordMetaDataOptionsProto.registerAllExtensions( extensionRegistry )


  private val sessionContexts = new ConcurrentHashMap[Long, FDBRecordContext]()
  private val sessionCommitTriggeredPromises = new ConcurrentHashMap[Long, Promise[Unit]]()
  private val sessionCommitResultPromises = new ConcurrentHashMap[Long, Promise[Unit]]()
  private val sessionRecordStores = new ConcurrentHashMap[Long, FDBRecordStore]()

  private def reloadFileDescriptorProto( fd: FileDescriptorProto ): FileDescriptorProto = {
    FileDescriptorProto.parseFrom( fd.toByteArray, extensionRegistry )
  }

  def newSession(): Future[Long] = {
    val sessionId = Random.nextLong()
    sessionCommitTriggeredPromises.put(sessionId, Promise[Unit]())
    sessionCommitResultPromises.put(sessionId, Promise[Unit]())
    val sessionContextSetPromise = Promise[Long]

    val func: java.util.function.Function[_ >: com.apple.foundationdb.record.provider.foundationdb.FDBRecordContext, java.util.concurrent.CompletableFuture[_]] = (ctx: FDBRecordContext) => {
      sessionContexts.put(sessionId, ctx)
      sessionContextSetPromise.success(sessionId)
      sessionCommitTriggeredPromises.get(sessionId).future.asJava.toCompletableFuture
    }

    fdb
      .runAsync(func)
      .asScala.onComplete {
        case Failure(ex) =>
          sessionContextSetPromise.failure(ex)
          sessionContexts.remove(sessionId)
          sessionCommitTriggeredPromises.remove(sessionId)
          sessionCommitResultPromises.get(sessionId).failure(ex)
        case Success(_) =>
          sessionContexts.remove(sessionId)
          sessionCommitTriggeredPromises.remove(sessionId)
          sessionCommitResultPromises.get(sessionId).success(())
      }
    sessionContextSetPromise.future
  }

  def commit(sessionId: Long): Future[_] = {
    val resultOpt = for {
      commitTriggeredPromise <- Option(sessionCommitTriggeredPromises.get(sessionId))
      commitResultPromise <- Option(sessionCommitResultPromises.get(sessionId))
    } yield {
      commitTriggeredPromise.success(())
      commitResultPromise.future
    }
    val resultF = resultOpt match {
      case Some(value) =>
        value
      case _ =>
        Future.failed(new NoSuchElementException(s"No session with $sessionId found."))
    }
    sessionCommitResultPromises.remove(sessionId)
    resultF
  }

  def newRecordStore(
                      sessionId: Long,
                      schema: FileDescriptorProto,
                      namespace: FDBDirectory
                    ): Future[RecordMetaDataProto.DataStoreInfo] = {
    Option(sessionContexts.get(sessionId)) match {
      case Some(ctx) =>
        val recordMetaData = RecordMetaData.build(
          FileDescriptor.buildFrom( reloadFileDescriptorProto(schema), Array.empty )
        )
        val resultF = FDBRecordStore.newBuilder()
          .setMetaDataProvider(recordMetaData)
          .setKeySpacePath(FDBDirectoryUtil.toKeySpacePath(namespace))
          .setContext(ctx)
          .createOrOpenAsync()
          .asScala
        resultF.map { recordStore =>
          sessionRecordStores.put(sessionId, recordStore)
          recordStore.getRecordStoreState.getStoreHeader
        }
      case None =>
        Future.failed(new NoSuchElementException(s"No session with $sessionId found."))
    }
  }

  def saveRecord(sessionId: Long, table: String, record: ByteString ): Future[Message] = {
    Option(sessionRecordStores.get(sessionId)) match {
      case Some(recordStore) =>
        Option(recordStore.getRecordMetaData.getRecordType(table)) match {
          case Some(recordType) =>
            val msg = DynamicMessage.parseFrom(recordType.getDescriptor, record, extensionRegistry)
            recordStore.saveRecordAsync(msg).asScala.map(stored => stored.getRecord)
          case None =>
            Future.failed(new NoSuchElementException(s"No record type in session $sessionId and table $table found."))
        }

      case None =>
        Future.failed(new NoSuchElementException(s"No record store in session $sessionId found."))
    }
  }

  def getRange( start: ByteString, end: ByteString ): Iterator[List[(ByteString, ByteString) ]] = {
    FDBUtil.fetchAll( fdb.database(), Some(new com.apple.foundationdb.Range( start.toByteArray, end.toByteArray ))  )
      .map( _.map( kv => ByteString.copyFrom(kv.getKey) -> ByteString.copyFrom(kv.getValue) ))
  }
  def getAll: Iterator[List[(ByteString, ByteString) ]] = {
    FDBUtil.fetchAll( fdb.database()  )
      .map( _.map( kv => ByteString.copyFrom(kv.getKey) -> ByteString.copyFrom(kv.getValue) ))
  }

  def deleteRecord(sessionId: Long, id: ByteString): Future[Boolean] = {
    Option(sessionRecordStores.get(sessionId)) match {
      case Some(recordStore) =>
        recordStore.deleteRecordAsync(Tuple.fromBytes(id.toByteArray)).asScala.map(_.booleanValue())
      case None =>
        Future.failed(new NoSuchElementException(s"No record store with $sessionId found."))
    }
  }

}


object FDBDirectoryUtil {
  def toKeySpacePath(namespace: FDBDirectory): KeySpacePath = {
    def buildDirectories(node: FDBDirectory): DirectoryLayerDirectory = {
      val parent = new DirectoryLayerDirectory(node.getName)
      if (node.hasSubDirectory)
        parent.addSubdirectory(buildDirectories(node.getSubDirectory))
      parent
    }

    val keySpace = new KeySpace(buildDirectories(namespace))

    @tailrec
    def addPath(child: FDBDirectory, path: KeySpacePath): KeySpacePath = {
      val newPath = path.add(child.getName, child.getValue)
      if (!child.hasSubDirectory) newPath
      else
        addPath(child.getSubDirectory, newPath)
    }

    val path = keySpace.path(namespace.getName, namespace.getValue)
    if (!namespace.hasSubDirectory) path
    else
      addPath(namespace.getSubDirectory, path)
  }
}