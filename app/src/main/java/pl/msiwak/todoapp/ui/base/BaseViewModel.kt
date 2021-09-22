package pl.msiwak.todoapp.ui.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import pl.msiwak.todoapp.util.error.Failure
import pl.msiwak.todoapp.util.event.Event

abstract class BaseViewModel<T: BaseEvent>: ViewModel() {

    protected val mErrorEvent: MutableLiveData<Event<Failure>> = MutableLiveData()
    val errorEvent : LiveData<Event<Failure>> = mErrorEvent

    protected val mEvent: MutableLiveData<Event<T>> = MutableLiveData()
    val event: LiveData<Event<T>> = mEvent

    fun sendEvent(event: T){
        mEvent.value = Event(event)
    }

    fun sendError(event: Failure){
        mErrorEvent.value = Event(event)
    }

}