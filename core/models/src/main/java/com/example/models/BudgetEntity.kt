package com.example.models

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

data class BudgetEntity(
    val id: Long,
    val type: ExpensesType,
    val value: Double,
    val year: String,
    val month: String,
    val day: String,
    val time: String
) {
    companion object {

        fun create(type: ExpensesType, value: Double, date: Date): BudgetEntity {
            val locale = Locale("ru", "RU")
            return BudgetEntity(
                id = 0,
                type = type,
                value = value,
                year = SimpleDateFormat("yyyy", locale).format(date),
                month = SimpleDateFormat("MM", locale).format(date),
                day = SimpleDateFormat("d", locale).format(date),
                time = SimpleDateFormat("HH:mm", locale).format(date),
            )
        }
    }
}

enum class ExpensesType {
    SPENDING, INCOME
}

