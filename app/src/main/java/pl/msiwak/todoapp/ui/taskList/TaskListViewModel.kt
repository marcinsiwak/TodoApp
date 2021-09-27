package pl.msiwak.todoapp.ui.taskList

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import pl.msiwak.todoapp.data.EditTaskData
import pl.msiwak.todoapp.data.Task
import pl.msiwak.todoapp.ui.base.BaseViewModel
import pl.msiwak.todoapp.util.firebase.FirebaseDatabase
import timber.log.Timber
import java.text.SimpleDateFormat
import java.util.*

class TaskListViewModel(
    private val firebaseDatabase: FirebaseDatabase
) :
    BaseViewModel<TaskListEvents>() {

    val tasksList: MutableLiveData<List<Task>> = MutableLiveData()
    var pages: MutableLiveData<List<Int>> = MutableLiveData()


    fun onInit() {
        viewModelScope.launch {
            firebaseDatabase.getTasks(onSuccess = {
                Timber.e("out: $it")
                tasksList.value = it
                pages.value = (1..it.size % 30).toList()

            }, onError = {

            })
        }
    }

    fun onAddTaskClicked() {
        sendEvent(TaskListEvents.NavigateToAddTask)
    }

    fun onEditTaskClicked(position: Int) {
        val task = tasksList.value?.get(position)
        sendEvent(TaskListEvents.NavigateToEditTask(EditTaskData(position, task)))

    }

    fun onItemChosenToRemove(position: Int){
        sendEvent(TaskListEvents.ShowDeleteQuestion(position))
    }

    fun onItemRemoved(position: Int){
        viewModelScope.launch {
            firebaseDatabase.deleteTask(position, onSuccess = {

            }, onError = {

            })

        }
    }


}