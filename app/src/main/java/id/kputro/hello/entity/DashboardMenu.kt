package id.kputro.hello.entity

import androidx.annotation.DrawableRes
import java.io.Serializable

data class DashboardMenu(
  val id: Int,
  val name: String,
  val applink: String
) : Serializable {
  var descriptions: String = ""
  var imageUrl: String? = null

  @DrawableRes
  var imageResId: Int? = null
}

