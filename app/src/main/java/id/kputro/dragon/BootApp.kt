package id.kputro.dragon

import android.app.Application
import id.kputro.bootstrap.startBootstrapModule
import id.kputro.dragon.core.datastore.users.UserDatastore
import id.kputro.dragon.core.startCoreApplication
import id.kputro.dragon.dimodule.ContentService
import id.kputro.dragon.dimodule.ContentServiceImpl
import id.kputro.dragon.ui.components.actionbar.ActionBarViewModel
import id.kputro.dragon.ui.dashboard.DashboardActivity
import id.kputro.dragon.ui.dashboard.DashboardViewModel
import id.kputro.dragon.ui.main.MainViewModel
import id.kputro.dragon.ui.menu.details.MenuDetailsActivity
import id.kputro.dragon.ui.menu.details.MenuDetailsViewModel
import id.kputro.dragon.utils.applink.Route
import id.kputro.pagestations.dsl.startPageStationsModule
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
    startCoreApp()
    startBootstrap()
    startPageStations()
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
    viewModel { MenuDetailsViewModel() }
  }

  // --
  private fun startCoreApp() {
    startCoreApplication {
      initiateLibrary(this@BootApp)
    }
  }

  // --
  private fun startBootstrap() {
    startBootstrapModule {
      // not used at the moment..
    }
  }

  private fun startPageStations() {
    startPageStationsModule {
      enableLog()

      registerActivity(Route.APP_SCHEME, Route.PAGE_DASHBOARD, DashboardActivity::class.java)
      registerActivity(Route.APP_SCHEME, Route.PAGE_MENU_DETAILS, MenuDetailsActivity::class.java)

      printAllPossibleRoute()
    }
  }


}