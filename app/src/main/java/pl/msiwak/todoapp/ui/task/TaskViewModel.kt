package pl.msiwak.todoapp.ui.task

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import pl.msiwak.todoapp.R
import pl.msiwak.todoapp.data.EditTaskData
import pl.msiwak.todoapp.data.Task
import pl.msiwak.todoapp.ui.base.BaseViewModel
import pl.msiwak.todoapp.util.error.Failure
import pl.msiwak.todoapp.util.firebase.FirebaseDatabase
import pl.msiwak.todoapp.util.helpers.ResourceProvider
import java.text.SimpleDateFormat
import java.util.*

class TaskViewModel(
    private val firebaseDatabase: FirebaseDatabase,
    private val resProvider: ResourceProvider
) : BaseViewModel<TaskEvents>() {

    companion object {
        const val DATE_FORMAT = "dd-MMM-yyyy"
    }

    val fragmentTitle: MutableLiveData<String> =
        MutableLiveData(resProvider.getString(R.string.add_task_title))
    val title: MutableLiveData<String> = MutableLiveData()
    val description: MutableLiveData<String> = MutableLiveData()
    val iconUrl: MutableLiveData<String> = MutableLiveData()
    val isEditMode: MutableLiveData<Boolean> = MutableLiveData()
    private var currentTask: EditTaskData? = null

    fun onInit(task: EditTaskData?) {
        currentTask = task
        task?.let {
            fragmentTitle.value = resProvider.getString(R.string.edit_task_title)
            title.value = it.task?.title
            description.value = it.task?.description
            isEditMode.value = true
        } ?: kotlin.run {
            fragmentTitle.value = resProvider.getString(R.string.add_task_title)
            isEditMode.value = false
        }
    }

    fun onAddClicked() {
        val testTask = Task(
            title.value,
            description.value,
            iconUrl.value,
            prepareCreationDate()
        )

        viewModelScope.launch {
            firebaseDatabase.addTask(testTask, onSuccess = {
                sendEvent(TaskEvents.TaskAdded(resProvider.getString(R.string.add_task_success)))
            }, onError = {
                sendError(Failure.AddTaskFailure(resProvider.getString(R.string.error_add_task)))
            })

        }
    }

    fun onUpdateClicked() {
        val updatedTask = currentTask?.task?.copy(
            title = title.value,
            description = description.value,
            iconUrl = iconUrl.value,
            creationDate = prepareCreationDate()
        )

        viewModelScope.launch {
            val pos = currentTask?.position
            if (pos != null && updatedTask != null) {
                firebaseDatabase.editTask(pos, updatedTask, onSuccess = {
                    sendEvent(TaskEvents.TaskEdited(resProvider.getString(R.string.update_task_success)))
                }, onError = {
                    sendError(Failure.UpdateTaskFailure(resProvider.getString(R.string.error_edit_task)))
                })
            }
        }
    }

    private fun prepareCreationDate(): String {
        val currentDate = Calendar.getInstance().time
        val df = SimpleDateFormat(DATE_FORMAT, Locale.getDefault())
        return df.format(currentDate)
    }
}