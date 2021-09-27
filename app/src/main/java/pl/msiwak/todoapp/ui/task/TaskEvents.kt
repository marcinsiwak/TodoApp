package pl.msiwak.todoapp.ui.task

import pl.msiwak.todoapp.ui.base.BaseEvent
import pl.msiwak.todoapp.ui.taskList.TaskListEvents

sealed class TaskEvents: BaseEvent {
    class TaskAdded(val infoText: String): TaskEvents()
    class TaskEdited(val infoText: String): TaskEvents()

}