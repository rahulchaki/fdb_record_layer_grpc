package io.fbd.util

import com.apple.foundationdb.tuple.{ByteArrayUtil, ByteArrayUtil2, Tuple}
import com.google.protobuf.UnknownFieldSet

import scala.util.{Failure, Success, Try}

trait ByteArrayReadable {
  def format(value: Array[Byte]): Try[String]
}

object ByteArrayTupleReadable extends ByteArrayReadable {
  override def format(value: Array[Byte]): Try[String] = Try(
    s"Tuple[${Tuple.fromBytes(value).toString}]"
  )
}

object ByteArrayProtobufReadable extends ByteArrayReadable {
  override def format(value: Array[Byte]): Try[String] = Try {
    s"Proto[${UnknownFieldSet.parseFrom(value).toString}]"
  }
}

object ByteArrayFDBReadable extends ByteArrayReadable {
  override def format(value: Array[Byte]): Try[String] = Success(
    s"FDB[${ByteArrayUtil.printable(value)}]"
  )
}

object ByteArrayTupleNFDBReadable extends ByteArrayReadable {
  override def format(value: Array[Byte]): Try[String] = {
    (ByteArrayTupleReadable.format(value), ByteArrayFDBReadable.format(value)) match {
      case (Success(tup), Success(fdb)) => Success(s"$tup</br>$fdb")
      case (Success(tup), _) => Success(tup)
      case (_, Success(fdb)) => Success(fdb)
      case _ => Failure(new IllegalArgumentException("Count not parsed"))
    }
  }
}

object ByteArrayHexReadable extends ByteArrayReadable {
  override def format(value: Array[Byte]): Try[String] = Success(
    s"Hex[${ByteArrayUtil2.toHexString(value)}]"
  )
}

class ComposableByteArrayReadable(inners: ByteArrayReadable*) extends ByteArrayReadable {
  override def format(value: Array[Byte]): Try[String] = {
    inners.find(inner => inner.format(value).isSuccess) match {
      case Some(inner) => inner.format(value)
      case None => Success("<<UNPARSED_VALUE>>")
    }
  }
}

object ByteArrayReadable {
  private val readable = new ComposableByteArrayReadable(
    ByteArrayProtobufReadable,
    ByteArrayTupleNFDBReadable,
    ByteArrayFDBReadable,
    ByteArrayHexReadable
  )

  def formatSafe(value: Array[Byte]): String = readable.format(value).get
}
