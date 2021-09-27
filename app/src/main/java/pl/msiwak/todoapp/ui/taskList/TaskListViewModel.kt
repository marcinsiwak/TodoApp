package pl.msiwak.todoapp.ui.taskList

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
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

    fun onInit() {
        viewModelScope.launch {
            firebaseDatabase.getTasks(onSuccess = {
                Timber.e("out: $it")
                tasksList.value = it

            }, onError = {

            })
        }
    }

    fun onAddClicked() {
        val currentDate = Calendar.getInstance().time
        val df = SimpleDateFormat("dd-MMM-yyyy", Locale.getDefault())
        val date = df.format(currentDate)
        val testTask = Task("testTitle", "testDescription", "url", date)

        viewModelScope.launch {
            firebaseDatabase.addTask(testTask, onSuccess = {
                sendEvent(TaskListEvents.TaskAdded("Task added"))
            }, onError = {

            })

        }
    }

    fun onItemRemoved(position: Int){
        viewModelScope.launch {
            firebaseDatabase.deleteTask(position)

        }
    }


}