package id.kputro.hello.core.extension

import android.content.Context
import android.content.SharedPreferences


const val PREFS_NAME = "id.kputro.hello"

object SharedPreferencesKeys {
  const val BASE_URL = "base_url"
  const val APP_TOKEN = "app_token"
  const val FIREBASE_TOKEN = "firebase_token"

  const val DEVICE_ID = "device_id"
  const val DEVICE_LATLNG = "device_latlng"
  const val DEVICE_IS_ROOT = "device_is_root"
  const val DEVICE_IS_CLONE = "device_is_clone"
  const val DEVICE_IS_EMULATOR = "device_is_emulator"

  const val USER_EMAIL = "user_email"
  const val USER_MODEL = "user_model"
  const val USER_IS_LOGIN = "user_is_login"
}

interface SharedPreferencesExtensionContract {
  fun Context.getDefault(): SharedPreferences

  fun Context.getBaseUrl(): String
  fun Context.setBaseUrl(mBaseUrl: String)

  fun Context.getAppToken(): String
  fun Context.setAppToken(mAppToken: String)
}

class SharedPreferencesExtension : SharedPreferencesExtensionContract {
  override fun Context.getDefault(): SharedPreferences {
    return this.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
  }

  override fun Context.getBaseUrl(): String {
    return this.getDefault().getString(SharedPreferencesKeys.BASE_URL, "") ?: ""
  }

  override fun Context.setBaseUrl(mBaseUrl: String) {
    this.getDefault().edit()
      .putString(SharedPreferencesKeys.BASE_URL, mBaseUrl)
      .apply()
  }

  override fun Context.getAppToken(): String {
    return this.getDefault().getString(SharedPreferencesKeys.APP_TOKEN, "") ?: ""
  }

  override fun Context.setAppToken(mAppToken: String) {
    this.getDefault().edit()
      .putString(SharedPreferencesKeys.APP_TOKEN, mAppToken)
      .apply()
  }
}

