package com.example.data.usecases

import com.example.data.repositories.ExpensesRepository
import com.example.models.BudgetEntity
import io.reactivex.Flowable
import javax.inject.Inject

interface GetAllRecordsUseCase {
    fun invoke(): Flowable<List<BudgetEntity>>

    class Impl @Inject constructor(
        private val expensesRepository: ExpensesRepository,
        ): GetAllRecordsUseCase {
        override fun invoke(): Flowable<List<BudgetEntity>> = expensesRepository.getAllRecords()
    }
}