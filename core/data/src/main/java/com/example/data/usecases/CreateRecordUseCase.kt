package com.example.data.usecases

import com.example.data.repositories.ExpensesRepository
import com.example.models.BudgetEntity
import com.example.models.ExpensesType
import io.reactivex.Single
import java.util.Date
import javax.inject.Inject

interface CreateRecordUseCase {

    fun invoke(value: Double, type: ExpensesType = ExpensesType.SPENDING): Single<Long>

    class Impl @Inject constructor(
        private val expensesRepository: ExpensesRepository
    ) : CreateRecordUseCase {
        override fun invoke(value: Double, type: ExpensesType): Single<Long> =
            expensesRepository.addRecord(
                BudgetEntity.create(
                    type = type,
                    value = value,
                    date = Date()
                )
            )
    }

}