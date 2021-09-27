package pl.msiwak.todoapp.ui.taskList

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import pl.msiwak.todoapp.databinding.ItemPageBinding

class PagerAdapter : RecyclerView.Adapter<PagerHolder>() {

    private var items: List<Int> = emptyList()

    private var onItemClicked: OnRecycleClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PagerHolder {
        return PagerHolder(
            ItemPageBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: PagerHolder, position: Int) {
        holder.bind(items[position], onItemClicked)
    }

    override fun getItemCount(): Int = items.size

    fun setData(items: List<Int>) {
        this.items = items
        notifyDataSetChanged()
    }

    fun setOnItemClickedListener(listener: OnRecycleClickListener) {
        onItemClicked = listener
    }


}

class PagerHolder(private val itemBinding: ItemPageBinding) :
    RecyclerView.ViewHolder(itemBinding.root) {

    fun bind(
        item: Int,
        clickListener: OnRecycleClickListener?
    ) {
        itemBinding.apply {

            root.setOnClickListener { clickListener?.onClick(adapterPosition) }

        }
    }

}