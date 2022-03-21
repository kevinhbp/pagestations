package id.kputro.hello.dimodule

import id.kputro.hello.BuildConfig
import id.kputro.hello.entity.AppVersion

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