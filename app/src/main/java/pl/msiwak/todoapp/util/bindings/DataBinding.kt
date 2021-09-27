package pl.msiwak.todoapp.util.bindings

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import pl.msiwak.todoapp.data.Task
import pl.msiwak.todoapp.ui.taskList.OnRecyclerListener
import pl.msiwak.todoapp.ui.taskList.TasksAdapter

@BindingAdapter("data")
fun <T> setRecyclerData(recyclerView: RecyclerView, items: List<Task>?) {
    if (recyclerView.adapter is TasksAdapter) {
        items?.let {
            (recyclerView.adapter as TasksAdapter).setData(it)
        }
    }
}

@BindingAdapter("onRecyclerClick")
fun setRecyclerListener(recyclerView: RecyclerView, onRecyclerListener: OnRecyclerListener) {
    if (recyclerView.adapter is TasksAdapter) {
        (recyclerView.adapter as TasksAdapter).setOnItemClickedListener(onRecyclerListener)
    }
}
