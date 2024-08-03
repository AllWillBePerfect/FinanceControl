package com.example.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.database.entities.ExpensesDbo.Companion.TABLE_NAME
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Entity(tableName = TABLE_NAME)
data class ExpensesDbo(
    @PrimaryKey(autoGenerate = true)
    val id: Long,
    val type: ExpensesTypeDbo,
    val value: Double,
    val year: String,
    val month: String,
    val day: String,
    val time: String
) {

    companion object {
        const val TABLE_NAME = "expenses_dbo"

        fun create(id: Long = 0, type: ExpensesTypeDbo, value: Double, date: Date): ExpensesDbo {
            val locale = Locale("ru", "RU")
            return ExpensesDbo(
                id = id,
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

enum class ExpensesTypeDbo {
    SPENDING, INCOME
}
