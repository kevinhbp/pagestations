package id.kputro.dragon.entity

import android.view.View
import java.io.Serializable
import id.kputro.dragon.R.drawable

class ActionBarBuilder : Serializable {
  var buttonBack: ButtonBuilder? = null
  var text: ButtonBuilder? = null
  var buttonA: ButtonBuilder? = null
  var buttonB: ButtonBuilder? = null

  fun withButtonBack(mButton: ButtonBuilder): ActionBarBuilder {
    this.buttonBack = mButton
    return this
  }

  fun withText(mText: ButtonBuilder): ActionBarBuilder {
    this.text = mText
    return this
  }

  fun withButtonA(mButton: ButtonBuilder): ActionBarBuilder {
    this.buttonA = mButton
    return this
  }

  fun withButtonB(mButton: ButtonBuilder): ActionBarBuilder {
    this.buttonB = mButton
    return this
  }

  // --
  fun getVisibilityButtonBack(): Int {
    if (buttonBack == null) return View.GONE
    return View.VISIBLE
  }

  fun getImageButtonBack(): Int {
    return buttonBack?.imageDrawable ?: drawable.ic_arrow_left
  }

  fun getTextTitle(): String {
    return text?.title ?: ""
  }

  fun getTextSubtitle(): String {
    return text?.subtitle ?: ""
  }

  fun getImageButtonA(): Int? {
    return buttonA?.imageDrawable
  }

  fun onClickButtonA(v: View?) {
    buttonA?.callback?.invoke()
  }

  fun getImageButtonB(): Int? {
    return buttonB?.imageDrawable
  }

  fun onClickButtonB(v: View?) {
    buttonB?.callback?.invoke()
  }
}

interface ActionBarBackgroundAlphaInterface {
  fun setAlpha(mAlpha: Float)
}