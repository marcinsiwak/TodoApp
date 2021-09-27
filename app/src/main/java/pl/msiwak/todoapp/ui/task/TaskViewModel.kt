package pl.msiwak.todoapp.ui.task

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import pl.msiwak.todoapp.data.EditTaskData
import pl.msiwak.todoapp.data.Task
import pl.msiwak.todoapp.ui.base.BaseViewModel
import pl.msiwak.todoapp.ui.taskList.TaskListEvents
import pl.msiwak.todoapp.ui.taskList.TaskListViewModel
import pl.msiwak.todoapp.util.firebase.FirebaseDatabase
import java.text.SimpleDateFormat
import java.util.*

class TaskViewModel(private val firebaseDatabase: FirebaseDatabase): BaseViewModel<TaskEvents>() {

    val title: MutableLiveData<String> = MutableLiveData()
    val description: MutableLiveData<String> = MutableLiveData()
    val isEditMode: MutableLiveData<Boolean> = MutableLiveData()
    private var currentTask: EditTaskData? = null

    fun onInit(task: EditTaskData?) {
        currentTask = task
        task?.let {
            title.value = it.task?.title
            description.value = it.task?.description
            isEditMode.value = true
        } ?: kotlin.run {
            isEditMode.value = false
        }
    }

    fun onAddClicked() {
        val currentDate = Calendar.getInstance().time
        val df = SimpleDateFormat("dd-MMM-yyyy", Locale.getDefault())
        val date = df.format(currentDate)
        val testTask = Task(title.value, description.value, "url", date)

        viewModelScope.launch {
            firebaseDatabase.addTask(testTask, onSuccess = {
                sendEvent(TaskEvents.TaskAdded("Task added"))
            }, onError = {

            })

        }
    }

    fun onUpdateClicked() {
        val currentDate = Calendar.getInstance().time
        val df = SimpleDateFormat("dd-MMM-yyyy", Locale.getDefault())
        val date = df.format(currentDate)
        val testTask = currentTask?.task?.copy(
            title = title.value,
            description = description.value,
            iconUrl = "url",
            creationDate = date
        )

        viewModelScope.launch {
            val pos = currentTask?.position
            if (pos != null && testTask != null){
                firebaseDatabase.editTask(pos, testTask, onSuccess = {
                    sendEvent(TaskEvents.TaskEdited("Task edited"))
                }, onError = {

                })


            }


        }
    }
}