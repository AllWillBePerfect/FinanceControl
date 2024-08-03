package com.example.finance.adapter.item_delegate

import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnClickListener
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.finance.R
import com.example.finance.adapter.models.BudgetUi
import com.example.finance.databinding.ItemSpendBinding
import com.example.models.ExpensesType
import com.example.ui.TextFormater
import com.example.ui.adapter.AdapterItemDelegate

class SpendingItemDelegate(
    private val onItemClickListener: (Long) -> Unit,
    val cornersDrawable: CornersDirectionsContainer
) :
    AdapterItemDelegate<BudgetUi>, OnClickListener {
    override fun onClick(v: View?) {
        if (v?.id == R.id.item_spending) {
            val item = v.tag as BudgetUi.Spending
            onItemClickListener.invoke(item.id)
        }
    }

    override fun forItem(item: BudgetUi): Boolean = item is BudgetUi.Spending

    override fun getViewHolder(parent: ViewGroup): ViewHolder {
        val binding = ItemSpendBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        binding.root.setOnClickListener(this)
        return SpendingViewHolder(binding)
    }

    override fun bindViewHolder(
        viewHolder: ViewHolder,
        item: BudgetUi,
        payloads: MutableList<Any>
    ) {
        (viewHolder as SpendingViewHolder).bind(item as BudgetUi.Spending)
    }

    inner class SpendingViewHolder(val binding: ItemSpendBinding) : ViewHolder(binding.root) {
        fun bind(item: BudgetUi.Spending) {
            when (item.type) {
                ExpensesType.SPENDING -> {
                    binding.value.text = TextFormater.formatMoneySpending(item.value)

                }
                ExpensesType.INCOME -> {
                    binding.value.text = TextFormater.formatMoneyIncome(item.value)
                }
            }
            binding.date.text = item.time
            binding.root.tag = item
            when (item.cornersDirection) {
                BudgetUi.CornersDirection.TOP -> binding.root.background =
                    cornersDrawable.topCornersDrawable

                BudgetUi.CornersDirection.BOTTOM -> binding.root.background =
                    cornersDrawable.bottomCornersDrawable

                BudgetUi.CornersDirection.ALL -> binding.root.background =
                    cornersDrawable.allCornersDrawable

                BudgetUi.CornersDirection.NOT -> binding.root.background =
                    cornersDrawable.notCornersDrawable
            }
        }

    }


    data class CornersDirectionsContainer(
        val topCornersDrawable: Drawable?,
        val bottomCornersDrawable: Drawable?,
        val allCornersDrawable: Drawable?,
        val notCornersDrawable: Drawable?,
    )

}