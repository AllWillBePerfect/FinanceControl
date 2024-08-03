package com.example.data.usecases

import com.example.data.repositories.ExpensesRepository
import io.reactivex.Completable
import javax.inject.Inject

interface DeleteRecordByIdUseCase {
    fun invoke(id: Long): Completable

    class Impl @Inject constructor(
        private val expensesRepository: ExpensesRepository
    ) : DeleteRecordByIdUseCase {
        override fun invoke(id: Long): Completable =
             expensesRepository.deleteRecordById(id)

    }
}