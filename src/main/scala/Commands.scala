package com.novoda

import com.android.ddmlib.IDevice
import com.novoda.android.FileOutputReceiver
import java.io.File

class Commands {

  type AndroidCommand = (IDevice => Unit)

  sealed trait Commands extends AndroidCommand

  case class Uninstall(pkg: String) extends Commands {
    def apply(device: IDevice) {
      device.uninstallPackage(pkg)
    }
  }

  case class Install(apkFile: String, reinstall: Boolean = true) extends Commands {
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


}