package com.example.gotravel.presentation.faq

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.gotravel.common.model.FAQ


class FAQAdapter: RecyclerView.Adapter<FAQViewHolder>(){

    var data = listOf<FAQ>()
        set(value) {
            val oldFAQList = data
            field = value
            val diffResult: DiffUtil.DiffResult = DiffUtil.calculateDiff(
                FAQDiffCallback(
                    oldFAQList,
                    data
                )
            )
            diffResult.dispatchUpdatesTo(this)
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FAQViewHolder {
        return FAQViewHolder.Item(parent)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: FAQViewHolder, position: Int) {
        val faq = data[position]
        (holder as FAQViewHolder.Item).bind(faq)
    }

    class FAQDiffCallback(
        private var oldFAQList: List<FAQ>,
        private var newFAQList: List<FAQ>
    ) : DiffUtil.Callback() {
        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            val oldFAQ = oldFAQList[oldItemPosition]
            val newFAQ = newFAQList[newItemPosition]
            return oldFAQ.id == newFAQ.id
        }

        override fun getOldListSize(): Int {
            return oldFAQList.size
        }

        override fun getNewListSize(): Int {
            return newFAQList.size
        }

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            val oldFAQ = oldFAQList[oldItemPosition]
            val newFAQ = newFAQList[newItemPosition]
            return oldFAQ == newFAQ
        }
    }

}