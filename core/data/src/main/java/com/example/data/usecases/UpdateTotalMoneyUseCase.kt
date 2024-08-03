package com.example.data.usecases

import com.example.data.repositories.TotalMoneyRepository
import com.example.models.ExpensesType
import com.example.models.MoneyTotalEntity
import io.reactivex.Completable
import javax.inject.Inject

interface UpdateTotalMoneyUseCase {

    fun execute(value: Double): Completable
    fun execute(value: Double, type: ExpensesType): Completable

    class Impl @Inject constructor(
        private val totalMoneyRepository: TotalMoneyRepository
    ) : UpdateTotalMoneyUseCase {
        override fun execute(value: Double): Completable {
            return totalMoneyRepository.getRecordSingle(1).flatMapCompletable {
                totalMoneyRepository.updateRecord(it.copy(value = value))
            }
        }

        override fun execute(value: Double, type: ExpensesType): Completable {
            return totalMoneyRepository.getRecordSingle(1).flatMapCompletable {
                val calculateValue = when (type) {
                     ExpensesType.SPENDING -> it.value - value
                     ExpensesType.INCOME -> it.value + value
                }
                totalMoneyRepository.updateRecord(it.copy(value = calculateValue))
            }
        }
    }
}