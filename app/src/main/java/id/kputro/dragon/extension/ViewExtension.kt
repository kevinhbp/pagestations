package id.kputro.dragon.extension

import androidx.databinding.Observable
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class ViewExtension {
  fun <T : Observable> T.addOnPropertyChanged(callback: (T) -> Unit) =
    addOnPropertyChangedCallback(
      object : Observable.OnPropertyChangedCallback() {
        @Suppress("UNCHECKED_CAST")
        override fun onPropertyChanged(
          observable: Observable?, i: Int
        ) =
          callback(observable as T)
      })

  fun <T : Observable> T.addOnPropertyChangeWithDebounce(
    waitMs: Long = 300L,
    coroutineScope: CoroutineScope, callback: (T) -> Unit
  ) {
    var debounceJob: Job? = null
    addOnPropertyChangedCallback(
      object : Observable.OnPropertyChangedCallback() {
        @Suppress("UNCHECKED_CAST")
        override fun onPropertyChanged(observable: Observable?, i: Int) {
          debounceJob?.cancel()
          debounceJob = coroutineScope.launch {
            delay(waitMs)
            callback(observable as T)
          }
        }
      }
    )
  }

  @Suppress("UNCHECKED_CAST")
  fun <T : RecyclerView> T.addOnScrollListener(callback: (T, dx: Int, dy: Int) -> Unit) =
    addOnScrollListener(
      object : RecyclerView.OnScrollListener() {
        override fun onScrolled(
          recyclerView: RecyclerView,
          dx: Int,
          dy: Int
        ) {
          callback(recyclerView as T, dx, dy)
          super.onScrolled(recyclerView, dx, dy)
        }
      })
}