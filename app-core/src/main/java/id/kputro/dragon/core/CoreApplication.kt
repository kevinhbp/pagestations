package id.kputro.dragon.core

import android.app.Application
import com.orhanobut.hawk.Hawk

typealias CoreAppDeclaration = CoreApplication.() -> Unit

fun startCoreApplication(declaration: CoreAppDeclaration): CoreApplication {
  val coreApp = CoreApplication.init()
  declaration.invoke(coreApp)
  return coreApp
}

class CoreApplication {

  // --
  fun initiateLibrary(app: Application) {
    // initiate library instance here
    initHawk(app)
  }

  private fun initHawk(app: Application) {
    Hawk.init(app.baseContext).build()
  }

  // --
  companion object {
    private lateinit var core: CoreApplication

    fun get(): CoreApplication {
      if (!::core.isInitialized) {
        throw RuntimeException("Core Application is not started..")
      }
      return core
    }

    fun init(): CoreApplication {
      core = CoreApplication()
      return core
    }
  }
}