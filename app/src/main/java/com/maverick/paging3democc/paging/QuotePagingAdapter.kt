package com.maverick.paging3democc.paging

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import com.maverick.paging3democc.models.Result
import androidx.recyclerview.widget.RecyclerView
import com.maverick.paging3democc.databinding.ItemQuoteBinding

class QuotePagingAdapter :
    PagingDataAdapter<Result, QuotePagingAdapter.QuoteViewHolder>(COMPARATOR) {

    interface EventListener {
        fun onItemClick(position: Int, item: Result)
    }

    private lateinit var mEventListener: EventListener

    fun setEventListener(eventListener: EventListener) {
        mEventListener = eventListener
    }

    inner class QuoteViewHolder(internal var itemBinding: ItemQuoteBinding) :
        RecyclerView.ViewHolder(itemBinding.root)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): QuoteViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val itemBinding = ItemQuoteBinding.inflate(inflater, parent, false)
        return QuoteViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: QuoteViewHolder, position: Int) {
        val currentItem = getItem(position)
        try {
            if (currentItem != null) {
                holder.itemBinding.apply {
                    quote.text = currentItem.content
                    root.setOnClickListener {
                        mEventListener.onItemClick(position, currentItem)
                    }
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    companion object {

        private val COMPARATOR = object : DiffUtil.ItemCallback<Result>() {
            override fun areItemsTheSame(oldItem: Result, newItem: Result): Boolean {
                return oldItem._id == newItem._id
            }

            override fun areContentsTheSame(oldItem: Result, newItem: Result): Boolean {
                return oldItem == newItem
            }

        }

    }

}