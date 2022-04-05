package id.kputro.dragon.entity

import java.io.Serializable

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
}

interface ActionBarBackgroundAlphaInterface {
  fun setAlpha(mAlpha: Float)
}