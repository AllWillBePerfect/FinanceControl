package com.example.data.usecases

import com.example.data.repositories.TotalMoneyRepository
import com.example.models.MoneyTotalEntity
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

interface CheckTotalMoneyUseCase {

    fun execute()

    class Impl @Inject constructor(
        private val totalMoneyRepository: TotalMoneyRepository
    ) : CheckTotalMoneyUseCase {
        override fun execute() {
            val sd = totalMoneyRepository.getRecordsCount()
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .subscribe { count ->
                    if (count == 0) {
                        totalMoneyRepository.insertRecord(
                            MoneyTotalEntity(
                                id = 1,
                                value = 0.0
                            )
                        ).subscribeOn(Schedulers.io())
                            .observeOn(Schedulers.io())
                            .subscribe()
                    }
                }
        }

    }
}