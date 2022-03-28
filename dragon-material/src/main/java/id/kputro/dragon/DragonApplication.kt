package id.kputro.dragon

import id.kputro.dragon.component.dialog.DragonDialog
import id.kputro.dragon.component.dialog.initDragonDialog
import id.kputro.dragon.router.DragonRouter
import id.kputro.dragon.router.initDragonRouter
import id.kputro.dragon.router.module.RouterModule

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

  val mRouter: DragonRouter = initDragonRouter { }

  val mDialog: DragonDialog = initDragonDialog {  }

  fun routerModule(mModule: RouterModule): DragonRouter {
    return mRouter.module(mModule)
  }

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