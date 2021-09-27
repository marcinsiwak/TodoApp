package pl.msiwak.todoapp.ui.taskList

import pl.msiwak.todoapp.data.Task
import pl.msiwak.todoapp.ui.base.BaseEvent

sealed class TaskListEvents: BaseEvent {
    class TaskAdded(val infoText: String): TaskListEvents()

}