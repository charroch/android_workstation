package com.novoda.android

import com.android.ddmlib.IShellOutputReceiver
import java.io.{FileOutputStream, File}

class FileOutputReceiver(f: File) extends IShellOutputReceiver {

  def isCancelled = false;

  val out = new FileOutputStream(f)

  def flush = {
    out.flush()
  };

  def addOutput(x: Array[Byte], y: Int, z: Int) = {
    out.write(x, y, z)
  }
}

class DefaultShellOutputReceiver extends IShellOutputReceiver {

  def isCancelled = false;

  def flush = {
  };

  def addOutput(x: Array[Byte], y: Int, z: Int) = {
    println(new String(x))
  }
}