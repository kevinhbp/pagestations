package id.kputro.dragon.material

import id.kputro.dragon.material.component.dialog.DragonDialog
import id.kputro.dragon.material.component.dialog.initDragonDialog

/**
 * Dragon Router Application
 *
 * @author Kevin Putro
 */

typealias DragonAppDeclaration = DragonApplication.() -> Unit

const val LOG_TAG = "dragonapp"

fun startDragonApplication(appDeclaration: DragonAppDeclaration): DragonApplication {
  val dragonApplication = DragonApplication.init()
  appDeclaration.invoke(dragonApplication)
  return dragonApplication
}

class DragonApplication {

  val mDialog: DragonDialog = initDragonDialog { }

  companion object {
    private lateinit var app: DragonApplication

    fun get(): DragonApplication {
      if (!::app.isInitialized) {
        throw RuntimeException("Dragon Application is not started..")
      }
      return app
    }

    fun init(): DragonApplication {
      app = DragonApplication()
      return app
    }
  }
}