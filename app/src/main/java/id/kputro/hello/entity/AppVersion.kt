package id.kputro.hello.entity

import java.io.Serializable

data class AppVersion(
  val versionCode: Int,
  val versionName: String,
  val buildTime: String,
  val flavorName: String
) : Serializable {
  fun getVersionText(): String {
    return "Version $versionName-$flavorName\n$versionCode:$buildTime"
  }
}