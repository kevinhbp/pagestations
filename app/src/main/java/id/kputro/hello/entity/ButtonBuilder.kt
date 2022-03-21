package id.kputro.hello.entity

import androidx.annotation.DrawableRes
import java.io.Serializable

data class ButtonBuilder(
  val title: String,
  val subtitle: String,
  @DrawableRes
  val imageDrawable: Int?,
  val callback: (() -> Unit)?
) : Serializable