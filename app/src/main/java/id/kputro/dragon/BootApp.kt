package id.kputro.dragon

import android.app.Application
import id.kputro.dragon.material.router.module.routerModule
import id.kputro.dragon.core.datastore.users.UserDatastore
import id.kputro.dragon.dimodule.ContentService
import id.kputro.dragon.dimodule.ContentServiceImpl
import id.kputro.dragon.material.startDragonApplication
import id.kputro.dragon.ui.components.actionbar.ActionBarViewModel
import id.kputro.dragon.ui.dashboard.DashboardActivity
import id.kputro.dragon.ui.dashboard.DashboardViewModel
import id.kputro.dragon.ui.main.MainViewModel
import id.kputro.dragon.utils.applink.Route
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.startKoin
import org.koin.dsl.module

/**
 * Project: Kevin Putro Profile
 * Code Name: Dragon
 *
 * @author kevinputro
 * @since 03/07/22
 * https://kputro.id
 */
class BootApp : Application() {

  override fun onCreate() {
    super.onCreate()
    startKoin()
    startDragonApp()
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
    single { UserDatastore() }
    single<ContentService> { ContentServiceImpl() }

    viewModel { ActionBarViewModel() }
    viewModel { MainViewModel(get(), get()) }
    viewModel { DashboardViewModel(get()) }
  }

  // --
  private fun startDragonApp() {
    startDragonApplication {
      routerModule(mRouterModule)
    }
  }

  private val mRouterModule = routerModule {
    registerActivity(Route.APP_SCHEME, Route.PAGE_DASHBOARD, DashboardActivity::class.java)
  }
}