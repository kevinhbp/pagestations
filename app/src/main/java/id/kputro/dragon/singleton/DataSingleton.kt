package id.kputro.dragon.singleton

import android.util.Log
import id.kputro.dragon.entity.MenuItemModel
import id.kputro.dragon.utils.const.Global

class DataSingleton {

  var selMenuItem: MenuItemModel? = null

  // --
  companion object {
    private lateinit var instance: DataSingleton

    fun get(): DataSingleton {
      if (!::instance.isInitialized) {
        instance = DataSingleton()
        Log.d(Global.LOG_TAG, "Data singleton initiated!")
      }

      return instance
    }
  }
}