package com.example.finance.adapter.models

import com.example.models.ExpensesType

sealed interface BudgetUi {
    data class Spending(
        val id: Long,
        val value: Double,
        val time: String,
        val cornersDirection: CornersDirection,
        val type: ExpensesType
    ) : BudgetUi

    data class Date(
        val date: String
    ) : BudgetUi

    enum class CornersDirection {
        TOP, BOTTOM, ALL, NOT
    }
}
