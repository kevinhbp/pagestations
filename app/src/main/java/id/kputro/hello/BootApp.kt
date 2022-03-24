package id.kputro.hello

import android.app.Application
import id.aksesmu.hello.dragon.module.dragonModule
import id.kputro.hello.dimodule.ContentService
import id.kputro.hello.dimodule.ContentServiceImpl
import id.kputro.hello.ui.components.actionbar.ActionBarViewModel
import id.kputro.hello.ui.dashboard.DashboardActivity
import id.kputro.hello.ui.dashboard.DashboardViewModel
import id.kputro.hello.ui.main.MainViewModel
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
  }

  // --
  private fun startKoin() {
    startKoin {
      androidLogger()
      androidContext(this@BootApp)
      modules(mAppModule)
    }
  }

  // --
  private val mAppModule = module {
    single<ContentService> { ContentServiceImpl() }

    viewModel { ActionBarViewModel() }
    viewModel { MainViewModel(get()) }
    viewModel { DashboardViewModel(get()) }
  }

  private val mDragonModule = dragonModule {
    registerActivity("dashboard", DashboardActivity::class.java)
  }
}