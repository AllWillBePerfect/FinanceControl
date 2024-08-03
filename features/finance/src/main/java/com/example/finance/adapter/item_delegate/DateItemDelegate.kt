package com.example.finance.adapter.item_delegate

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.finance.adapter.models.BudgetUi
import com.example.finance.databinding.ItemDateBinding
import com.example.ui.adapter.AdapterItemDelegate

class DateItemDelegate : AdapterItemDelegate<BudgetUi> {
    override fun forItem(item: BudgetUi): Boolean = item is BudgetUi.Date

    override fun getViewHolder(parent: ViewGroup): RecyclerView.ViewHolder {
        val binding = ItemDateBinding.inflate(
            LayoutInflater.from(parent.context),
            parent, false
        )
        return DateViewHolder(binding)
    }

    override fun bindViewHolder(
        viewHolder: RecyclerView.ViewHolder,
        item: BudgetUi,
        payloads: MutableList<Any>
    ) {
        (viewHolder as DateViewHolder).bind(item as BudgetUi.Date)
    }

    inner class DateViewHolder(val binding: ItemDateBinding) : ViewHolder(binding.root) {
        fun bind(item: BudgetUi.Date) {
            binding.dateTextView.text = item.date
        }
    }

}