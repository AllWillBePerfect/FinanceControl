package com.example.data.mappers

import com.example.database.entities.ExpensesDbo
import com.example.database.entities.ExpensesTypeDbo
import com.example.database.entities.MoneyTotalDbo
import com.example.models.BudgetEntity
import com.example.models.ExpensesType
import com.example.models.MoneyTotalEntity
import java.util.Date

fun ExpensesType.toExpensesTypeDbo(): ExpensesTypeDbo = when (this) {
    ExpensesType.SPENDING -> ExpensesTypeDbo.SPENDING
    ExpensesType.INCOME -> ExpensesTypeDbo.INCOME
}

fun ExpensesTypeDbo.toExpensesType(): ExpensesType = when (this) {
    ExpensesTypeDbo.SPENDING -> ExpensesType.SPENDING
    ExpensesTypeDbo.INCOME -> ExpensesType.INCOME
}

fun BudgetEntity.createNewExpensesDbo(): ExpensesDbo = ExpensesDbo.create(
    type = type.toExpensesTypeDbo(),
    value = value,
    date = Date()
)

fun BudgetEntity.toExpensesDbo(): ExpensesDbo = ExpensesDbo(
    id = id,
    type = type.toExpensesTypeDbo(),
    value = value,
    year = year,
    month = month,
    day = day,
    time = time
)

fun ExpensesDbo.toBudgetEntity(): BudgetEntity = BudgetEntity(
    id = id,
    type = type.toExpensesType(),
    value = value,
    year = year,
    month = month,
    day = day,
    time = time

)

fun MoneyTotalDbo.toEntity() = MoneyTotalEntity(
    id = id,
    value = value
)


fun MoneyTotalEntity.toDbo() = MoneyTotalDbo(
    id = id,
    value = value
)
