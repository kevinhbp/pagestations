package id.kputro.hello

import android.app.Application
import id.aksesmu.hello.dragon.module.dragonModule
import id.aksesmu.hello.dragon.tools.startDragonRouter
import id.kputro.hello.dimodule.ContentService
import id.kputro.hello.dimodule.ContentServiceImpl
import id.kputro.hello.ui.components.actionbar.ActionBarViewModel
import id.kputro.hello.ui.dashboard.DashboardActivity
import id.kputro.hello.ui.dashboard.DashboardViewModel
import id.kputro.hello.ui.main.MainViewModel
import id.kputro.hello.utils.applink.Route
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.startKoin
import org.koin.dsl.module

/**
 * Project Kevin Putro Profile
 * @author kevinputro
 * @since 03/07/22
 * https://kputro.id
 */
class BootApp : Application() {

  override fun onCreate() {
    super.onCreate()
    startKoin()
    startRouter()
  }

  // --
  private fun startKoin() {
    startKoin {
      androidLogger()
      androidContext(this@BootApp)
      modules(mAppModule)
    }
  }

  private val mAppModule = module {
    single<ContentService> { ContentServiceImpl() }

    viewModel { ActionBarViewModel() }
    viewModel { MainViewModel(get()) }
    viewModel { DashboardViewModel(get()) }
  }

  // --
  private fun startRouter() {
    startDragonRouter {
      module(mDragonModule)
    }
  }

  private val mDragonModule = dragonModule {
    registerActivity(Route.APP_SCHEME, Route.PAGE_DASHBOARD, DashboardActivity::class.java)
  }
}