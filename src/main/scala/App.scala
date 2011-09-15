package com.novoda

import android.FileOutputReceiver
import com.android.ddmlib.{AndroidDebugBridge, IDevice}
import scopt.OptionParser
import java.io.File

class App extends xsbti.AppMain {
  def run(config: xsbti.AppConfiguration) = {
    Exit(App.run(config.arguments))
  }
}

object App {

  implicit def defaultFilter(d: IDevice) = true

  def run(args: Array[String]): Int = {
    println("Hello World: " + args.mkString(" "))
    if (parser.parse(args)) {
      // AndroidTools().devices()      parser

      parser.showUsage
    }
    0
  }

  def main(args: Array[String]) {
    System.exit(run(args))
  }

  class Config {
    var foo: Int = 0;
    var bar: String = "";
    var xyz: Boolean = true
    var libname: String = ""
    var libfile: String = ""
    var whatnot: String = ""
  }

  val config: Config = new Config


  val parser = new OptionParser("sadb") {

    arg("<singlefile>", "<singlefile> is an argument", {
      v: String => config.whatnot = v
    })

    intOpt("f", "foo", "foo is an integer property", {
      v: Int => config.foo = v
    })

    opt("o", "output", "<file>", "output is a string property", {
      v: String => config.bar = v
    })

    booleanOpt("xyz", "xyz is a boolean property", {
      v: Boolean => config.xyz = v
    })

    keyValueOpt("l", "lib", "<libname>", "<filename>", "load library <libname>", {
      (key: String, value: String) => {
        println(key + "  = " + value)
        config.libname = key;
        config.libfile = value
      }
    })

  }

}

class AndroidTools(adb: AndroidDebugBridge) {
  implicit def allDeviceFilter(d: IDevice): DeviceFilter = {
    _ => true
  }
    implicit def t(d:IDevice):Boolean = {
    true
  }


  def listDevices = {
    println("Devices attached %s" format ("all devices"))
    devices
  }

  type DeviceFilter = (IDevice => Boolean)


  def f: DeviceFilter = {
    _ => true
  }

  def devices(implicit filter: (IDevice => Boolean)): Seq[IDevice] = {
    adb.getDevices.filter(filter)
  }

  def execute(command: (IDevice => Unit))(implicit filter: (IDevice => Boolean)) {
    devices.foreach(command)
  }
}

object AndroidTools {
  type DeviceFilter = (IDevice => Boolean)

  implicit def allDeviceFilter(d: IDevice): DeviceFilter = {
    _ => true
  }

  implicit def t(d:IDevice):Boolean = {
    true
  }

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
      device.executeShellCommand("am start -a android.intent.action.MAIN -n %s/%s" format(pkg, klass), new FileOutputReceiver(new File("/tmp/test")))
    }
  }

  def apply(): AndroidTools = {
    try {
      AndroidDebugBridge.init(true)
    } catch {
      case e: Exception =>
      //AndroidDebugBridge.createBridge()
    }
    new AndroidTools(AndroidDebugBridge.createBridge)
  }

}

case class Exit(val code: Int) extends xsbti.Exit