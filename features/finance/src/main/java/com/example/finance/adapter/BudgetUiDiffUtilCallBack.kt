package com.example.finance.adapter

import com.example.finance.adapter.models.BudgetUi
import com.example.ui.adapter.CustomDiffUtilCallback

class BudgetUiDiffUtilCallBack : CustomDiffUtilCallback<BudgetUi> {
    override fun areItemsTheSame(
        oldItemPosition: Int,
        newItemPosition: Int,
        oldList: List<BudgetUi>,
        newList: List<BudgetUi>
    ): Boolean {
        val oldItem = oldList[oldItemPosition]
        val newItem = newList[newItemPosition]
        return if (oldItem is BudgetUi.Spending && newItem is BudgetUi.Spending)
            oldItem.id == newItem.id
        else if(oldItem is BudgetUi.Date && newItem is BudgetUi.Date)
            oldItem.date == newItem.date
        else false
    }

    override fun areContentsTheSame(
        oldItemPosition: Int,
        newItemPosition: Int,
        oldList: List<BudgetUi>,
        newList: List<BudgetUi>
    ): Boolean {
        val oldItem = oldList[oldItemPosition]
        val newItem = newList[newItemPosition]
        return oldItem == newItem
    }

    override fun getChangePayload(
        oldItemPosition: Int,
        newItemPosition: Int,
        oldList: List<BudgetUi>,
        newList: List<BudgetUi>
    ): Any? {
        val oldItem = oldList[oldItemPosition]
        val newItem = newList[newItemPosition]
        return if (oldItem is BudgetUi.Spending && newItem is BudgetUi.Spending)
            oldItem.id == newItem.id
        else if(oldItem is BudgetUi.Date && newItem is BudgetUi.Date)
            oldItem.date == newItem.date
        else false
    }
}