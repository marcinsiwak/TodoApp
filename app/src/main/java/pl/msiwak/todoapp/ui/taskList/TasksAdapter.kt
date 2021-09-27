package pl.msiwak.todoapp.ui.taskList

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import pl.msiwak.todoapp.data.Task
import pl.msiwak.todoapp.databinding.ItemTaskBinding

class TasksAdapter : RecyclerView.Adapter<TasksHolder>() {

    private var items: List<Task> = emptyList()

    private var onItemClicked: OnRecycleClickListener? = null
    private var onLongItemClicked: OnRecyclerLongClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TasksHolder {
        return TasksHolder(
            ItemTaskBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: TasksHolder, position: Int) {
        holder.bind(items[position], onItemClicked, onLongItemClicked)
    }

    override fun getItemCount(): Int = items.size

    fun setData(items: List<Task>) {
        this.items = items
        notifyDataSetChanged()
    }

    fun setOnItemClickedListener(listener: OnRecycleClickListener) {
        onItemClicked = listener
    }

    fun setOnItemLongClickedListener(listener: OnRecyclerLongClickListener) {
        onLongItemClicked = listener
    }


}

class TasksHolder(private val itemBinding: ItemTaskBinding) :
    RecyclerView.ViewHolder(itemBinding.root) {

    fun bind(item: Task, clickListener: OnRecycleClickListener?, longClickListener: OnRecyclerLongClickListener?) {
        itemBinding.taskItemTitleTv.text = item.title
        itemBinding.taskItemDescriptionTv.text = item.description
        itemBinding.taskItemDateTv.text = item.creationDate
        itemBinding.root.setOnClickListener { clickListener?.onClick(adapterPosition) }
        itemBinding.root.setOnLongClickListener {
            longClickListener?.onLongClicked(adapterPosition)
            true
        }
    }

}