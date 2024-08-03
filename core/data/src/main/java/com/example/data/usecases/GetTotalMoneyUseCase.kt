package com.example.data.usecases

import com.example.data.repositories.TotalMoneyRepository
import com.example.models.MoneyTotalEntity
import io.reactivex.Flowable
import javax.inject.Inject

interface GetTotalMoneyUseCase {
    fun execute(id: Long = 1): Flowable<MoneyTotalEntity>
    class Impl @Inject constructor(
        private val totalMoneyRepository: TotalMoneyRepository
    ) : GetTotalMoneyUseCase {
        override fun execute(id: Long): Flowable<MoneyTotalEntity> =
            totalMoneyRepository.getRecord(id)
    }
}