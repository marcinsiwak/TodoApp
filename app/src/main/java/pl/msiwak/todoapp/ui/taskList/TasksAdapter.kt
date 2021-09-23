package pl.msiwak.todoapp.ui.taskList

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import pl.msiwak.todoapp.databinding.ItemTaskBinding

class TasksAdapter : RecyclerView.Adapter<TasksHolder>() {

    private var items: List<String> = emptyList()

    private var onItemClicked: (() -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TasksHolder {
        return TasksHolder(
            ItemTaskBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: TasksHolder, position: Int) {
        holder.bind(items[position], onItemClicked)
    }

    override fun getItemCount(): Int = items.size

    fun setData(items: List<String>) {
        this.items = items
    }

    fun setOnItemClickedListener(listener: (() -> Unit)?) {
        onItemClicked = listener
    }


}

class TasksHolder(private val itemBinding: ItemTaskBinding) : RecyclerView.ViewHolder(itemBinding.root) {

    fun bind(item: String, listener:(() -> Unit)?){
        itemBinding.taskItemTitleTv.text = item
    }

}