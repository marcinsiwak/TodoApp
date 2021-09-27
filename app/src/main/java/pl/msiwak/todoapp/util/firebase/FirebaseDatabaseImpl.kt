package pl.msiwak.todoapp.util.firebase

import androidx.lifecycle.MutableLiveData
import com.google.firebase.firestore.FirebaseFirestore
import pl.msiwak.todoapp.data.Task
import timber.log.Timber
import java.io.Serializable

class FirebaseDatabaseImpl : FirebaseDatabase {

    companion object {
        const val COLLECTION_NAME = "tasks_collection"
        const val DOCUMENT = "tasks"
        const val TASK_ARRAY_KEY = "tasks_array"
    }

    private val db by lazy { FirebaseFirestore.getInstance() }
    private val tasks: MutableLiveData<ArrayList<Task>?> = MutableLiveData()

    override suspend fun getTasks(onSuccess: (List<Task>) -> Unit, onError: () -> Unit) {
        db.collection(COLLECTION_NAME)
            .document(DOCUMENT)
            .addSnapshotListener { value, _ ->
                Timber.e("outputTaskCollection: ${value}")
                val tasksCollection = value?.toObject(TasksCollection::class.java)
                Timber.e("outputTaskCollection: ${tasksCollection?.tasks}")
                tasks.value = tasksCollection?.tasks
                tasksCollection?.tasks?.let { onSuccess.invoke(it) }
                Timber.e("output: ${tasksCollection?.tasks}")
            }
    }

    override suspend fun addTask(task: Task, onSuccess: () -> Unit, onError: () -> Unit) {
        tasks.value?.let {
            val newList = it
            newList.add(task)
            db.collection(COLLECTION_NAME)
                .document(DOCUMENT)
                .update(DOCUMENT, newList)
                .addOnSuccessListener {
                    onSuccess.invoke()
                    Timber.e("Success")
                }
                .addOnFailureListener { e ->
                    onError.invoke()
                    Timber.e("Failure, $e")
                }

        } ?: run {
            db.collection(COLLECTION_NAME)
                .document(DOCUMENT)
                .set(TasksCollection(arrayListOf(task)))
                .addOnSuccessListener {
                    onSuccess.invoke()
                    Timber.e("Success")
                }
                .addOnFailureListener { e ->
                    onError.invoke()
                    Timber.e("Failure, $e")
                }
        }
    }


    override suspend fun deleteTask(position: Int, onSuccess: () -> Unit, onError: () -> Unit) {
        tasks.value?.let {
            val newList = it
            newList.removeAt(position)
            db.collection(COLLECTION_NAME)
                .document(DOCUMENT)
                .update(DOCUMENT, newList)
                .addOnSuccessListener {
//                    onSuccess.invoke()
                    Timber.e("Success")
                }
                .addOnFailureListener { e ->
                    Timber.e("Failure, $e")
                }

        }
    }

    override suspend fun editTask(
        position: Int,
        task: Task,
        onSuccess: () -> Unit,
        onError: () -> Unit
    ) {
        tasks.value?.let {
            val newList = it
            newList[position] = task
            db.collection(COLLECTION_NAME)
                .document(DOCUMENT)
                .update(DOCUMENT, newList)
                .addOnSuccessListener {
                    onSuccess.invoke()
                    Timber.e("Success")
                }
                .addOnFailureListener { e ->
                    Timber.e("Failure, $e")
                }

        }
    }

}


data class TasksCollection(val tasks: java.util.ArrayList<Task>) : Serializable {
    constructor() : this(arrayListOf<Task>())
}