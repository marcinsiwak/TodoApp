package pl.msiwak.todoapp.ui.taskList

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import pl.msiwak.todoapp.R
import pl.msiwak.todoapp.data.EditTaskData
import pl.msiwak.todoapp.data.Page
import pl.msiwak.todoapp.data.Task
import pl.msiwak.todoapp.ui.base.BaseViewModel
import pl.msiwak.todoapp.util.error.Failure
import pl.msiwak.todoapp.util.firebase.FirebaseDatabase
import pl.msiwak.todoapp.util.helpers.ResourceProvider
import timber.log.Timber

class TaskListViewModel(
    private val firebaseDatabase: FirebaseDatabase,
    private val resProvider: ResourceProvider
) :
    BaseViewModel<TaskListEvents>() {

    val tasksList: MutableLiveData<List<Task>> = MutableLiveData()
    val tasksToDisplayList: MutableLiveData<List<Task>> = MutableLiveData()
    val pages: MutableLiveData<List<Page>> = MutableLiveData()
    val currentPage: MutableLiveData<Int> = MutableLiveData(0)
    val isLoaderVisible: MutableLiveData<Boolean> = MutableLiveData(false)

    fun onInit() {
        isLoaderVisible.value = true
        viewModelScope.launch {
            firebaseDatabase.getTasks(onSuccess = {
                isLoaderVisible.value = false
                tasksList.value = it
                currentPage.value = 0
                val pagesNumber = kotlin.math.ceil(it.size.toDouble().div(30)).toInt()
                val pagesList = (1..pagesNumber).toList()
                pages.value = pagesList.map { number ->
                    if (number == 0) {
                        Page(number, true)
                    } else {
                        Page(number, false)
                    }
                }
                prepareList()

            }, onError = {
                isLoaderVisible.value = false
                sendError(Failure.GetTaskFailure(resProvider.getString(R.string.error_get_tasks)))
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

    fun onItemChosenToRemove(position: Int) {
        sendEvent(TaskListEvents.ShowDeleteQuestion(position))
    }

    fun onItemRemoved(position: Int) {
        viewModelScope.launch {
            firebaseDatabase.deleteTask(position, onSuccess = {
                sendEvent(TaskListEvents.ShowTaskDeletedMessage(resProvider.getString(R.string.remove_task_success)))
            }, onError = {
                sendError(Failure.RemoveTaskFailure(resProvider.getString(R.string.error_remove_task)))
            })

        }
    }

    fun onPageClicked(position: Int) {
        currentPage.value = position
        prepareList()
    }

    fun onNewPageLoaded() {
        val page = currentPage.value
        val pagesSize = pages.value?.size
        if (page != null && pagesSize != null && page < pagesSize - 1) {
            currentPage.value = currentPage.value?.plus(1)
            prepareList()
        }
    }

    private fun prepareList() {
        val page = currentPage.value ?: 0
        tasksToDisplayList.value = tasksList.value?.filterIndexed { index, _ ->
            index >= 0 + 30 * page && index <= 29 + 30 * page
        }

        pages.value = pages.value?.map {
            if (it.number == page + 1) {
                it.copy(isSelected = true)
            } else {
                it.copy(isSelected = false)
            }
        }

    }

}