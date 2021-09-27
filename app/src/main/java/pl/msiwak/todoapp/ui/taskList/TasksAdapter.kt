package pl.msiwak.todoapp.ui.taskList

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import pl.msiwak.todoapp.data.Task
import pl.msiwak.todoapp.databinding.ItemTaskBinding

class TasksAdapter : RecyclerView.Adapter<TasksHolder>() {

    private var items: List<Task> = emptyList()

    private var onItemClicked: OnRecyclerListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TasksHolder {
        return TasksHolder(
            ItemTaskBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: TasksHolder, position: Int) {
        holder.bind(items[position], onItemClicked)
    }

    override fun getItemCount(): Int = items.size

    fun setData(items: List<Task>) {
        this.items = items
        notifyDataSetChanged()
    }

    fun setOnItemClickedListener(listener: OnRecyclerListener) {
        onItemClicked = listener
    }


}

class TasksHolder(private val itemBinding: ItemTaskBinding) : RecyclerView.ViewHolder(itemBinding.root) {

    fun bind(item: Task, listener: OnRecyclerListener?){
        itemBinding.taskItemTitleTv.text = item.title
        itemBinding.root.setOnClickListener { listener?.onClick(adapterPosition) }
    }

}