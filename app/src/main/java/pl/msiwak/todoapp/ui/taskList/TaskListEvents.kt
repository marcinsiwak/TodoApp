package pl.msiwak.todoapp.ui.taskList

import pl.msiwak.todoapp.data.EditTaskData
import pl.msiwak.todoapp.data.Task
import pl.msiwak.todoapp.ui.base.BaseEvent

sealed class TaskListEvents: BaseEvent {
    object NavigateToAddTask: TaskListEvents()
    class ShowDeleteQuestion(val position: Int): TaskListEvents()
    class NavigateToEditTask(val task: EditTaskData?): TaskListEvents()

}