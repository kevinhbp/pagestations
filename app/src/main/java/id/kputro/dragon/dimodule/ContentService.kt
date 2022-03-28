package id.kputro.dragon.dimodule

import id.kputro.dragon.BuildConfig
import id.kputro.dragon.entity.AppVersion

interface ContentService {
  fun getAppVersion(): AppVersion
}

class ContentServiceImpl : ContentService {
  override fun getAppVersion(): AppVersion {
    return AppVersion(
      BuildConfig.VERSION_CODE,
      BuildConfig.VERSION_NAME,
      BuildConfig.BUILD_TIME,
      BuildConfig.FLAVOR
    )
  }
}