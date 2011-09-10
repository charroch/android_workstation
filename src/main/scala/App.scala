package com.novoda

import com.android.ddmlib.{IShellOutputReceiver, AndroidDebugBridge, IDevice}

class App extends xsbti.AppMain {
  def run(config: xsbti.AppConfiguration) = {
    Exit(App.run(config.arguments))
  }
}

object App {

  implicit def defaultFilter(d: IDevice) = true

  def run(args: Array[String]): Int = {
    println("Hello World: " + args.mkString(" "))
    AndroidTools().devices()
    0
  }

  def main(args: Array[String]) {
    System.exit(run(args))
  }
}

class AndroidTools(adb: AndroidDebugBridge) {

  def listDevices = {
    println("Devices attached %s" format ("all devices"))
    //devices
  }

  def f(d: IDevice) = true

  def devices(filter: (IDevice => Boolean) = f): Seq[IDevice] = {
    adb.getDevices.filter(filter)
  }

  def execute(command: (IDevice => Unit)) {
    devices().foreach(command)
  }
}

object AndroidTools {

  implicit def allDeviceFilter(d: IDevice) = true

  type AndroidCommand = (IDevice => Unit)

  trait Commands extends AndroidCommand

  class Uninstall(pkg: String) extends Commands {
    def apply(device: IDevice) {
      device.uninstallPackage(pkg)
    }
  }

  class Install(apkFile: String, reinstall: Boolean = true) extends Commands {
    def apply(device: IDevice) {
      device.installPackage(apkFile, reinstall)
    }
  }

  trait Shell extends Commands

  trait Activity extends Shell


  case class Start(pkg: String, klass: String) extends Activity {
    def apply(device: IDevice) {
      device.executeShellCommand("am start -a android.intent.action.MAIN -n %s/%s" format(pkg, klass), new RR)
    }
  }

  def apply() = {
    try {
      AndroidDebugBridge.init(true)
      val adb = AndroidDebugBridge.createBridge
      new AndroidTools(adb)
    }
  }

}

case class Exit(val code: Int) extends xsbti.Exit

class RR extends IShellOutputReceiver {
  def isCancelled = false;

  def flush = {};

  def addOutput(x: Array[Byte], y: Int, z: Int) = {}
}


class DefaultShellOutputReceiver extends IShellOutputReceiver {

  def isCancelled = false;

  def flush = {
  };

  def addOutput(x: Array[Byte], y: Int, z: Int) = {
    println(new String(x))
  }
}