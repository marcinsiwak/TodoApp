package pl.msiwak.todoapp.util.bindings

import android.view.View
import androidx.core.view.isVisible
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import pl.msiwak.todoapp.data.Page
import pl.msiwak.todoapp.data.Task
import pl.msiwak.todoapp.ui.taskList.OnRecycleClickListener
import pl.msiwak.todoapp.ui.taskList.OnRecyclerLongClickListener
import pl.msiwak.todoapp.ui.taskList.PagerAdapter
import pl.msiwak.todoapp.ui.taskList.TasksAdapter

@BindingAdapter("data")
fun <T> setRecyclerData(recyclerView: RecyclerView, items: List<Task>?) {
    if (recyclerView.adapter is TasksAdapter) {
        items?.let {
            (recyclerView.adapter as TasksAdapter).setData(it)
        }
    }
}

@BindingAdapter("pagerData")
fun <T> setPagerRecyclerData(recyclerView: RecyclerView, items: List<Page>?) {
    if (recyclerView.adapter is PagerAdapter) {
        items?.let {
            (recyclerView.adapter as PagerAdapter).setData(it)
        }
    }
}


@BindingAdapter("onPagerRecyclerClick")
fun setPagerRecyclerListener(recyclerView: RecyclerView, onRecyclerListener: OnRecycleClickListener) {
    if (recyclerView.adapter is PagerAdapter) {
        (recyclerView.adapter as PagerAdapter).setOnItemClickedListener(onRecyclerListener)
    }
}

@BindingAdapter("onRecyclerClick")
fun setRecyclerListener(recyclerView: RecyclerView, onRecyclerListener: OnRecycleClickListener) {
    if (recyclerView.adapter is TasksAdapter) {
        (recyclerView.adapter as TasksAdapter).setOnItemClickedListener(onRecyclerListener)
    }
}

@BindingAdapter("onRecyclerLongClick")
fun setRecyclerLongClickListener(recyclerView: RecyclerView, onRecyclerListener: OnRecyclerLongClickListener) {
    if (recyclerView.adapter is TasksAdapter) {
        (recyclerView.adapter as TasksAdapter).setOnItemLongClickedListener(onRecyclerListener)
    }
}

@BindingAdapter("isVisible")
fun setVisibility(view: View, isVisible: Boolean){
    if (isVisible) {
        view.visibility = View.VISIBLE
    } else {
        view.visibility = View.GONE
    }
}
