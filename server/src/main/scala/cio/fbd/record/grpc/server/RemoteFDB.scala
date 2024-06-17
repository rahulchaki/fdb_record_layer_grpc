package cio.fbd.record.grpc.server

import cio.fbd.util.FDBUtil
import cio.fdb.record.grpc.FdbRecordGrpc.FDBDirectory
import cio.fdb.record.grpc.Filters
import com.apple.foundationdb.record.metadata.{Key, RecordType}
import com.apple.foundationdb.record.provider.foundationdb.FDBMetaDataStore.MissingMetaDataException
import com.apple.foundationdb.record.provider.foundationdb._
import com.apple.foundationdb.record.provider.foundationdb.keyspace._
import com.apple.foundationdb.record.query.RecordQuery
import com.apple.foundationdb.record.{RecordMetaData, RecordMetaDataOptionsProto, RecordMetaDataProto}
import com.apple.foundationdb.tuple.Tuple
import com.google.protobuf.DescriptorProtos.FileDescriptorProto
import com.google.protobuf.Descriptors.FileDescriptor
import com.google.protobuf.{ByteString, DynamicMessage, ExtensionRegistry, Message}

import java.util.concurrent.{CompletableFuture, ConcurrentHashMap}
import scala.annotation.tailrec
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.{Future, Promise}
import scala.jdk.CollectionConverters.{CollectionHasAsScala, IterableHasAsJava}
import scala.jdk.FutureConverters._
import scala.util.{Failure, Random, Success, Try}


object RemoteFDB {
  private val extensionRegistry = ExtensionRegistry.newInstance()
  RecordMetaDataOptionsProto.registerAllExtensions(extensionRegistry)

  def loadExisting(fdb: FDBDatabase, namespace: List[String]): Option[RemoteFDB]= {
    fdb.run{ ctx =>
      val keySpace = FDBDirectoryUtil.toKeySpacePath(namespace)
      val metadataStore = new FDBMetaDataStore(ctx, keySpace)
      Try(metadataStore.getRecordMetaData) match {
        case Failure(_: MissingMetaDataException) =>
          None
        case Failure(ex) =>
          throw ex
        case Success(metadata) =>
          Some(new RemoteFDB(fdb, keySpace, metadata))
      }
    }
  }

  def buildNew(fdb: FDBDatabase, namespace: List[String], schema: FileDescriptorProto): RemoteFDB = {
    val schemaFD = RemoteFDB.toFileDescriptor(schema)
    val keySpace = FDBDirectoryUtil.toKeySpacePath(namespace)
    val rmBuilder = RecordMetaData.newBuilder()
    rmBuilder.setRecords(schemaFD)
    val recordMetaData = rmBuilder.build()
    fdb.run { ctx =>
      val metadataStore = new FDBMetaDataStore(ctx, keySpace)
      metadataStore.saveRecordMetaData(recordMetaData)
      new RemoteFDB(fdb, keySpace, recordMetaData)
    }
  }

  private def toFileDescriptor(fd: FileDescriptorProto): FileDescriptor = {
    FileDescriptor.buildFrom(
      FileDescriptorProto.parseFrom(fd.toByteArray, extensionRegistry),
      Array.empty
    )
  }

  def toMessage(record: ByteString, recordType: RecordType): Message = {
    DynamicMessage.parseFrom(recordType.getDescriptor, record, extensionRegistry)
  }

  def toTuple(value: AnyRef): Tuple = {
    value match {
      case inner: ByteString =>
        Tuple.fromBytes(inner.toByteArray)
      case inner: Iterable[_] =>
        Key.Evaluated.concatenate(inner.asJava).toTuple
      case inner: java.util.Collection[_] =>
        Key.Evaluated.concatenate(inner).toTuple
      case inner =>
        Key.Evaluated.scalar(inner).toTuple
    }
  }
}

class RemoteFDB(fdb: FDBDatabase, keySpace: KeySpacePath, val metadata: RecordMetaData) {
  private val rsBuilder = FDBRecordStore.newBuilder()
    .setMetaDataProvider(metadata)


  def primaryKey(table: String, record: ByteString): Tuple = {
    val recordType = metadata.getRecordType(table)
    val storedRecord = FDBStoredRecord
      .newBuilder(RemoteFDB.toMessage(record, recordType))
      .setRecordType(recordType)
      .build()
    val primaryKeyExpression = recordType.getPrimaryKey
    primaryKeyExpression.evaluateSingleton(storedRecord).toTuple
  }

  def createAll(table: String, records: List[ByteString]): List[ByteString] = {
    val recordType = metadata.getRecordType(table)
    fdb.run { ctx =>
      val recordStore = rsBuilder.copyBuilder().setContext(ctx).setKeySpacePath(keySpace).createOrOpen()
      records
        .map(rec => RemoteFDB.toMessage(rec, recordType))
        .map(recordStore.saveRecord)
        .map(_.getPrimaryKey.pack())
        .map(ByteString.copyFrom)
    }
  }

  def loadAll(table: String, keys: List[AnyRef]): List[ByteString] = {
    fdb.run { ctx =>
      val recordStore = rsBuilder.copyBuilder().setContext(ctx).setKeySpacePath(keySpace).createOrOpen()
      keys.map(RemoteFDB.toTuple).map(recordStore.loadRecord).map(_.getRecord.toByteString)
    }
  }

  def deleteAll(table: String, keys: List[AnyRef]): Boolean = {
    fdb.run { ctx =>
      val recordStore = rsBuilder.copyBuilder().setContext(ctx).setKeySpacePath(keySpace).createOrOpen()
      keys.map(RemoteFDB.toTuple).forall(recordStore.deleteRecord)
    }
  }

  def loadAll(table: String, query: Filters.BooleanQuery): Future[List[ByteString]] = {
    val jFuture: CompletableFuture[java.util.List[FDBQueriedRecord[Message]]] =
      fdb.run { ctx =>
        val recordStore = rsBuilder.copyBuilder().setContext(ctx).setKeySpacePath(keySpace).createOrOpen()
        val rQuery = RecordQuery.newBuilder().setRecordType(table).setFilter(FDBUtil.toFDBQuery(query)).build()
        val cursor = recordStore.executeQuery(rQuery)
        cursor.asList()
      }
    jFuture.asScala.map(_.asScala.map(_.getRecord.toByteString).toList)
  }

  def deleteAll(table: String, query: Filters.BooleanQuery): Boolean = {
    fdb.run { ctx =>
      val recordStore = rsBuilder.copyBuilder().setContext(ctx).setKeySpacePath(keySpace).createOrOpen()
      recordStore.deleteRecordsWhere(table, FDBUtil.toFDBQuery(query))
      true
    }
  }

}

class RemoteFDBSessionManager(fdb: FDBDatabase) {

  private val extensionRegistry = ExtensionRegistry.newInstance()
  RecordMetaDataOptionsProto.registerAllExtensions(extensionRegistry)


  private val sessionContexts = new ConcurrentHashMap[Long, FDBRecordContext]()
  private val sessionCommitTriggeredPromises = new ConcurrentHashMap[Long, Promise[Unit]]()
  private val sessionCommitResultPromises = new ConcurrentHashMap[Long, Promise[Unit]]()
  private val sessionRecordStores = new ConcurrentHashMap[Long, FDBRecordStore]()

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
        val recordMetaData = RecordMetaData.build(toFileDescriptor(schema))
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

  private def toFileDescriptor(fd: FileDescriptorProto): FileDescriptor = {
    FileDescriptor.buildFrom(
      FileDescriptorProto.parseFrom(fd.toByteArray, extensionRegistry),
      Array.empty
    )
  }

  def saveRecord(sessionId: Long, table: String, record: ByteString): Future[Message] = {
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


  def loadRecords(sessionId: Long, table: String, query: Filters.BooleanQuery, handler: Message => Unit): Future[Void] = {
    Option(sessionRecordStores.get(sessionId)) match {
      case Some(recordStore) =>
        val rQuery = RecordQuery.newBuilder()
          .setRecordType(table)
          .setFilter(FDBUtil.toFDBQuery(query))
          .build()
        val cursor = recordStore.executeQuery(rQuery)
        cursor.forEach { record: FDBQueriedRecord[Message] =>
          handler(record.getRecord)
        }.asScala

      case None =>
        Future.failed(new NoSuchElementException(s"No record store in session $sessionId found."))
    }
  }

  def getRange(start: ByteString, end: ByteString): Iterator[List[(ByteString, ByteString)]] = {
    FDBUtil.fetchAll(fdb.database(), Some(new com.apple.foundationdb.Range(start.toByteArray, end.toByteArray)))
      .map(_.map(kv => ByteString.copyFrom(kv.getKey) -> ByteString.copyFrom(kv.getValue)))
  }

  def getAll: Iterator[List[(ByteString, ByteString)]] = {
    FDBUtil.fetchAll(fdb.database())
      .map(_.map(kv => ByteString.copyFrom(kv.getKey) -> ByteString.copyFrom(kv.getValue)))
  }

  def deleteRecord(sessionId: Long, id: ByteString): Future[Boolean] = {
    Option(sessionRecordStores.get(sessionId)) match {
      case Some(recordStore) =>
        recordStore.deleteRecordAsync(Tuple.fromBytes(id.toByteArray)).asScala.map(_.booleanValue())
      case None =>
        Future.failed(new NoSuchElementException(s"No record store with $sessionId found."))
    }
  }

  def loadRecord(sessionId: Long) = {

  }

}


object FDBDirectoryUtil {
  def toKeySpacePath(namespace: List[String]): KeySpacePath = {
    def buildDirectories(rest: List[String]): DirectoryLayerDirectory = {
      val dir = new DirectoryLayerDirectory(rest.head)
      rest.tail.tail match {
        case Nil => dir
        case further =>
          dir.addSubdirectory(buildDirectories(further))
          dir
      }
    }

    val keySpace = new KeySpace(buildDirectories(namespace))

    @tailrec
    def addPath(rest: List[String], path: KeySpacePath): KeySpacePath = {
      val newPath = path.add(rest.head, rest.tail.head)
      rest.tail.tail match {
        case Nil => newPath
        case further => addPath(further, newPath)
      }
    }

    val path = keySpace.path(namespace.head, namespace.tail.head)
    namespace.tail.tail match {
      case Nil => path
      case further =>
        addPath(further, path)
    }
  }
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