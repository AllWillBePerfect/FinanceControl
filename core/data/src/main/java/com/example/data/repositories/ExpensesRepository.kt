package com.example.data.repositories

import android.util.Log
import com.example.data.mappers.toBudgetEntity
import com.example.data.mappers.createNewExpensesDbo
import com.example.data.mappers.toExpensesDbo
import com.example.database.dao.ExpensesDao
import com.example.models.BudgetEntity
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

interface ExpensesRepository {

    fun addRecord(entity: BudgetEntity): Single<Long>
    fun deleteRecord(entity: BudgetEntity): Completable
    fun deleteRecordById(id: Long): Completable
    fun findRecordById(id: Long): Single<BudgetEntity>
    fun getAllRecords(): Flowable<List<BudgetEntity>>

    class Impl @Inject constructor(
        private val expensesDao: ExpensesDao
    ) : ExpensesRepository {

        override fun addRecord(entity: BudgetEntity) =
            expensesDao.insertRecord(entity.createNewExpensesDbo())

        override fun deleteRecord(entity: BudgetEntity): Completable =
            expensesDao.deleteRecord(entity.toExpensesDbo())

        override fun deleteRecordById(id: Long): Completable =
            expensesDao.deleteRecordById(id)

        override fun findRecordById(id: Long): Single<BudgetEntity> =
            expensesDao.getRecordById(id).map { it.toBudgetEntity() }
                .doOnSuccess { Log.d("ExpensesRepository success", it.toString()) }
                .doOnError { Log.d("ExpensesRepository error", it.toString()) }
                .doOnDispose { Log.d("ExpensesRepository error", "dispose") }

        override fun getAllRecords(): Flowable<List<BudgetEntity>> =
            expensesDao.getAllRecords()
                .map { list ->
                    list.map { item -> item.toBudgetEntity() }
                }
    }
}