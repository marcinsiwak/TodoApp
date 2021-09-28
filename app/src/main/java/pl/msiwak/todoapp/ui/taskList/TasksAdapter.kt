package pl.msiwak.todoapp.ui.taskList

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import pl.msiwak.todoapp.common.setImageUrl
import pl.msiwak.todoapp.data.Task
import pl.msiwak.todoapp.databinding.ItemTaskBinding
import timber.log.Timber

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

    fun bind(
        item: Task,
        clickListener: OnRecycleClickListener?,
        longClickListener: OnRecyclerLongClickListener?
    ) {
        itemBinding.apply {
            taskItemTitleTv.text = item.title
            taskItemDescriptionTv.text = item.description
            taskItemDateTv.text = item.creationDate
            taskItemIv.setImageUrl(item.iconUrl)
            root.setOnClickListener { clickListener?.onClick(adapterPosition) }
            root.setOnLongClickListener {
                longClickListener?.onLongClicked(adapterPosition)
                true
            }
        }
    }

}