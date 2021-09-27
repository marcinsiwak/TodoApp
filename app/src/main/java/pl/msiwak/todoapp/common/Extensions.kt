package pl.msiwak.todoapp.common

import android.widget.ImageView
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import com.bumptech.glide.Glide
import pl.msiwak.todoapp.util.error.Failure
import pl.msiwak.todoapp.util.event.Event

inline fun <T : Any, L : LiveData<Event<T>>> LifecycleOwner.observeEvent(
    liveData: L,
    crossinline body: (T?) -> Unit
) {
    liveData.observe(this, { body(it.peekContent()) })
}

inline fun <T : Failure, L : LiveData<Event<T>>> LifecycleOwner.observeFailure(
    liveData: L,
    crossinline body: (T?) -> Unit
) {
    liveData.observe(this, { body(it.peekContent()) })
}

fun ImageView.setImageUrl(imageUrl: String?){
    Glide.with(this.context)
        .load(imageUrl)
        .placeholder(android.R.drawable.ic_menu_my_calendar)
        .error(android.R.drawable.ic_menu_my_calendar)
        .into(this)
}