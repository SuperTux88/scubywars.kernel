package de.tdng2011.game.util

import java.nio.ByteBuffer
import java.io.DataInputStream

object StreamUtil {
  def read(stream : DataInputStream, count : Int) : ByteBuffer = {
    val byteArray = new Array[Byte](count)
    stream.readFully(byteArray)
    ByteBuffer.wrap(byteArray)
  }
}