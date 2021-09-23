package pl.msiwak.todoapp.common

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
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