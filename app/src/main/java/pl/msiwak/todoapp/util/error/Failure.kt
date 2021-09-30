package pl.msiwak.todoapp.util.error

sealed class Failure {
    class GetTaskFailure(val errorText: String): Failure()
    class AddTaskFailure(val errorText: String): Failure()
    class UpdateTaskFailure(val errorText: String): Failure()
    class RemoveTaskFailure(val errorText: String): Failure()
    class EmptyFieldsFailure(val errorText: String): Failure()
}