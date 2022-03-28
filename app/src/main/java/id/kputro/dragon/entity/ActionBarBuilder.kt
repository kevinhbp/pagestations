package id.kputro.dragon.entity

import java.io.Serializable

class ActionBarBuilder : Serializable {
  var text: ButtonBuilder? = null
  var logo: ButtonBuilder? = null
  var buttonA: ButtonBuilder? = null
  var buttonB: ButtonBuilder? = null

  fun withText(mText: ButtonBuilder): ActionBarBuilder {
    this.text = mText
    return this
  }

  fun withLogo(mLogo: ButtonBuilder): ActionBarBuilder {
    this.logo = mLogo
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