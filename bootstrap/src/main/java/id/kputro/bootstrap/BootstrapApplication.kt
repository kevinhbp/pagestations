package id.kputro.bootstrap

import id.kputro.bootstrap.dialog.Dialog

/**
 * Bootstrap Application
 *
 * @author Kevin Putro
 * @since 22 April 2022
 */
const val LOG_TAG = "bootstrap"

typealias BootstrapAppDeclaration = BootstrapApplication.() -> Unit

fun startBootstrapModule(appDeclaration: BootstrapAppDeclaration): BootstrapApplication {
  val app = BootstrapApplication.init()
  appDeclaration.invoke(app)
  return app
}

class BootstrapApplication {

  val mDialog = Dialog()

  companion object {
    private lateinit var app: BootstrapApplication

    fun get(): BootstrapApplication {
      if (!::app.isInitialized) {
        throw RuntimeException("Bootstrap Application is not initialized.")
      }
      return app
    }

    fun init(): BootstrapApplication {
      app = BootstrapApplication()
      return app
    }
  }
}