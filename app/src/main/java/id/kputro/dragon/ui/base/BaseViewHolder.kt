package id.kputro.dragon.ui.base

import android.content.Context
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView

abstract class BaseViewHolder<M, N>(val view: ViewDataBinding) :
  RecyclerView.ViewHolder(view.root) {

  protected var context: Context = view.root.context

  @Suppress("UNCHECKED_CAST")
  protected fun <T : ViewDataBinding> getBinding(): T {
    return (view as T)
  }

  abstract fun bind(mModel: M, mListener: N)
}