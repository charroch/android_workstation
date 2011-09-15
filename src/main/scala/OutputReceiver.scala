package com.novoda.android

import java.io.{FileOutputStream, File}
import com.android.ddmlib.{MultiLineReceiver, IShellOutputReceiver}

class FileOutputReceiver(f: File) extends MultiLineReceiver {

  def out = new FileOutputStream(f)

  def processNewLines(lines: Array[String]) {
  }

  def isCancelled = false
}

class DefaultShellOutputReceiver extends IShellOutputReceiver {

  def isCancelled = false;

  def flush = {
  };

  def addOutput(x: Array[Byte], y: Int, z: Int) = {
    println(new String(x))
  }
}