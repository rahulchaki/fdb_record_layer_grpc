package cio.fbd.record.rpc

import cio.fbd.util.FDBUtil
import cio.fdb.record.grpc.FdbMetadataManager.FDBDatabaseMetadata
import com.apple.foundationdb.record.metadata.RecordType
import com.apple.foundationdb.record.{RecordMetaData, RecordMetaDataOptionsProto}
import com.apple.foundationdb.record.provider.foundationdb.FDBMetaDataStore.MissingMetaDataException
import com.apple.foundationdb.record.provider.foundationdb.{FDBDatabase, FDBMetaDataStore}
import com.apple.foundationdb.record.provider.foundationdb.keyspace.KeySpacePath
import com.apple.foundationdb.tuple.Tuple
import com.google.protobuf.DescriptorProtos.FileDescriptorProto
import com.google.protobuf.Descriptors.FileDescriptor
import com.google.protobuf.{ByteString, DynamicMessage, ExtensionRegistry, Message}

import java.util.concurrent.ConcurrentHashMap
import scala.util.{Failure, Success, Try}


case class Database(
                     name: String,
                     version: Int,
                     metadataKeySpace: KeySpacePath,
                     metadataPath: Tuple,
                     recordsKeySpace: KeySpacePath,
                     recordsPath: Tuple,
                     metadata: RecordMetaData
                   ){
  def toProto: FDBDatabaseMetadata = {
    FDBDatabaseMetadata.newBuilder()
      .setName( name)
      .setVersion( version)
      .setMetadataNamespace( ByteString.copyFrom(metadataPath.pack()))
      .setRecordsNamespace( ByteString.copyFrom(recordsPath.pack()))
      .setMetadata( metadata.toProto)
      .build()
  }
}

trait MetadataManager[M[_]]{
  def cached( database: String ): Option[Database]
  def fetch( database: String ): M[ Option[Database] ]
  def open( database: String ): M[ Option[Database] ]
  def create(
                   database: String,
                   schema: FileDescriptorProto
                 ): M[Database]
  def createOrOpen(
                    database: String,
                    schema: FileDescriptorProto
                  ): M[Database]
}

object MetadataManager {

  private val extensionRegistry = ExtensionRegistry.newInstance()
  RecordMetaDataOptionsProto.registerAllExtensions(extensionRegistry)

  def toFileDescriptor(fd: FileDescriptorProto): FileDescriptor = {
    FileDescriptor.buildFrom(
      FileDescriptorProto.parseFrom(fd.toByteArray, extensionRegistry),
      Array.empty
    )
  }

  def toMessage(record: ByteString, recordType: RecordType): Message = {
    DynamicMessage.parseFrom(recordType.getDescriptor, record, extensionRegistry)
  }


  def sync( fdb: FDBDatabase, baseNamespace: List[String]): MetadataManagerSync = {
    new MetadataManagerSync( fdb, baseNamespace )
  }



}

class MetadataManagerSync(
                           fdb: FDBDatabase,
                           baseNamespace: List[String]
                         ) extends MetadataManager[Try]{



  private val databases = new ConcurrentHashMap[String, Database]()

  private def buildKeySpaces( database: String ): (KeySpacePath, KeySpacePath) = {
    val databaseNamespace = baseNamespace appendedAll List("database", database)
    val metadataNamespace = databaseNamespace appendedAll List("dataType", "metadata")
    val recordsNamespace = databaseNamespace appendedAll List("dataType", "records")
    FDBUtil.toKeySpacePath( metadataNamespace ) -> FDBUtil.toKeySpacePath( recordsNamespace )
  }

  override def cached(database: String): Option[Database] = {
    Option(databases.get(database))
  }

  override def fetch(database: String): Try[Option[Database]] = {
    val ( metadataNamespace, recordsNamespace ) = buildKeySpaces(database)
    fdb.run{ ctx =>
      val metadataStore = new FDBMetaDataStore(ctx, metadataNamespace)
      Try(metadataStore.getRecordMetaData) match {
        case Failure(_: MissingMetaDataException) =>
          Success( None )
        case Failure(ex) =>
          Failure( ex )
        case Success(metadata) =>
          val namespaces = metadataNamespace.toTuple(ctx) ->  recordsNamespace.toTuple(ctx)
          val db = Database(database, metadata.getVersion, metadataNamespace, namespaces._1, recordsNamespace, namespaces._2, metadata)
          databases.put( database, db )
          Success(Some(db))
      }
    }
  }

  override def open(database: String): Try[Option[Database]] = {
    Option(databases.get(database)) match {
      case Some( db ) =>
        Success( Some( db) )
      case None =>
        fetch( database )
    }
  }


  override def create(
                       database: String,
                       schema: FileDescriptorProto
                     ): Try[Database] = Try{

    val ( metadataNamespace, recordsNamespace ) = buildKeySpaces(database)
    val schemaFD = MetadataManager.toFileDescriptor(schema)
    val rmBuilder = RecordMetaData.newBuilder()
    rmBuilder.setRecords(schemaFD)
    val metadata = rmBuilder.build()
    val db = fdb.run { ctx =>
      val metadataStore = new FDBMetaDataStore(ctx, metadataNamespace)
      metadataStore.saveRecordMetaData(metadata)
      val namespaces = metadataNamespace.toTuple(ctx) ->  recordsNamespace.toTuple(ctx)
      Database(database, metadata.getVersion, metadataNamespace, namespaces._1, recordsNamespace, namespaces._2, metadata)
    }
    databases.put( database, db )
    db
  }

  override def createOrOpen(
                             database: String,
                             schema: FileDescriptorProto
                           ): Try[Database] = {
    open(database) match {
      case Failure(_) => create( database, schema )
      case Success(None) => create( database, schema )
      case Success( Some(db) ) => Success(db)
    }
  }


}