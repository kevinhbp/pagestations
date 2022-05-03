package id.kputro.dragon.ui.components.actionbar

import android.content.Intent
import androidx.databinding.ObservableField
import androidx.databinding.ObservableFloat
import androidx.lifecycle.ViewModel
import id.kputro.dragon.entity.ActionBarBuilder
import id.kputro.dragon.ui.base.BaseView
import id.kputro.dragon.ui.base.BaseViewModel
import id.kputro.dragon.ui.components.actionbar.ActionBarContract.ActionBarViewContract

interface ActionBarContract {
  interface ActionBarViewModelContract : BaseViewModel<ActionBarViewContract> {

  }

  interface ActionBarViewContract : BaseView {
    fun getActionBarBuilder(): ActionBarBuilder
  }
}

class ActionBarViewModel : ViewModel(),
  ActionBarContract.ActionBarViewModelContract {

  val alphaBackground = ObservableFloat(0.8f)

  val model = ObservableField<ActionBarBuilder>()

  private lateinit var view: ActionBarViewContract

  override fun init(view: ActionBarViewContract, data: Intent?) {
    this.view = view
  }

  override fun start() {
    // setup default model
    setup(ActionBarBuilder())

    // setup with new model
    setup(this.view.getActionBarBuilder())
  }

  private fun setup(mModel: ActionBarBuilder) {
    this.model.set(mModel)
  }
}