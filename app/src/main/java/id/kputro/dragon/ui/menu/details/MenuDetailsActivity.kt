package id.kputro.dragon.ui.menu.details

import android.os.Bundle
import id.kputro.dragon.R.layout
import id.kputro.dragon.databinding.MenuDetailsActivityBinding
import id.kputro.dragon.ui.base.BaseActivity
import org.koin.androidx.viewmodel.ext.android.viewModel

class MenuDetailsActivity : BaseActivity<MenuDetailsActivityBinding>(layout.menu_details_activity),
  MenuDetailsContract.MenuDetailsViewContract {

  private lateinit var binding: MenuDetailsActivityBinding

  private val menuDetailsViewModel: MenuDetailsViewModel by viewModel()

  override fun initDataBinding(binding: MenuDetailsActivityBinding) {
    this.binding = binding
    this.binding.menuDetailsViewModel = this.menuDetailsViewModel
    this.binding.executePendingBindings()
  }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    menuDetailsViewModel.init(this, intent)
  }

  override fun onCompleteDraw() {
    super.onCompleteDraw()
    menuDetailsViewModel.start()
  }
}
