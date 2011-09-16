package com.novoda

import com.android.ddmlib.IDevice

class RichDevice(device: IDevice) {

  def ! = {

  }

  //def apply(builder: JProcessBuilder): ProcessBuilder = new Simple(builder)
}

object RichDevice {

  def apply(serialNumber:String) = {

  }

  implicit def toRichDevice(device: IDevice) = new RichDevice(device)
}

trait ProcessImplicits {

  import scala.sys.process._

  //implicit def deviceToProcess(device: RichDevice): ProcessBuilder = apply(command)
}