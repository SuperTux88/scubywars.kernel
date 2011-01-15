package de.tdng2011.game.kernel

import java.io.{BufferedOutputStream, ByteArrayOutputStream}
import java.util.zip.GZIPOutputStream

/**
 * Created by IntelliJ IDEA.
 * User: fbe
 * Date: 1/14/11
 * Time: 10:01 PM
 * To change this template use File | Settings | File Templates.
 */

object Compressor {
  def gZipString(string : String): Array[Byte] = {
    val byteOutputStream = new ByteArrayOutputStream
    val bufferedOutputStream = new BufferedOutputStream(new GZIPOutputStream(byteOutputStream))
    bufferedOutputStream.write(string.getBytes)
    bufferedOutputStream.close
    val returnValue = byteOutputStream.toByteArray
    byteOutputStream.close
    returnValue
  }
}