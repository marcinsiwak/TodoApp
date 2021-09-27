package pl.msiwak.todoapp.util.firebase

import pl.msiwak.todoapp.data.Task

interface FirebaseDatabase {
    suspend fun getTasks(onSuccess: (List<Task>) -> Unit, onError: () -> Unit)
    suspend fun addTask(task: Task, onSuccess: () -> Unit, onError: () -> Unit)
    suspend fun deleteTask(position: Int)
    suspend fun editTask()
}