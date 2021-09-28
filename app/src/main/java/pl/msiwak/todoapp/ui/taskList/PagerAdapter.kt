package pl.msiwak.todoapp.ui.taskList

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import pl.msiwak.todoapp.R
import pl.msiwak.todoapp.data.Page
import pl.msiwak.todoapp.databinding.ItemPageBinding
import timber.log.Timber

class PagerAdapter : RecyclerView.Adapter<PagerHolder>() {

    private var items: List<Page> = emptyList()

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

    fun setData(items: List<Page>) {
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
        item: Page,
        clickListener: OnRecycleClickListener?
    ) {
        itemBinding.apply {
            itemPagerTv.text = item.number.toString()
            itemPagerTv.background = if (item.isSelected) {
                ContextCompat.getDrawable(itemBinding.root.context, R.drawable.bg_pager_selected)
            } else {
                ContextCompat.getDrawable(itemBinding.root.context, R.drawable.bg_pager_ripple)
            }
            root.setOnClickListener { clickListener?.onClick(adapterPosition) }
        }
    }

}