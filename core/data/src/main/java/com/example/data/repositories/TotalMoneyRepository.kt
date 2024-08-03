package com.example.data.repositories

import com.example.data.mappers.toDbo
import com.example.data.mappers.toEntity
import com.example.database.dao.TotalMoneyDao
import com.example.models.MoneyTotalEntity
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single
import javax.inject.Inject

interface TotalMoneyRepository {

    fun insertRecord(record: MoneyTotalEntity): Completable
    fun updateRecord(entity: MoneyTotalEntity): Completable
    fun getRecord(id: Long): Flowable<MoneyTotalEntity>
    fun getRecordSingle(id: Long): Single<MoneyTotalEntity>
    fun getRecordsCount(): Single<Int>

    class Impl @Inject constructor(
        private val totalMoneyDao: TotalMoneyDao
    ) : TotalMoneyRepository {
        override fun insertRecord(record: MoneyTotalEntity): Completable =
            totalMoneyDao.insertRecord(record.toDbo())

        override fun updateRecord(entity: MoneyTotalEntity): Completable =
            totalMoneyDao.updateRecord(entity.toDbo())


        override fun getRecord(id: Long): Flowable<MoneyTotalEntity> =
            totalMoneyDao.findRecord(id).map { it.toEntity() }

        override fun getRecordSingle(id: Long): Single<MoneyTotalEntity> =
            totalMoneyDao.findRecordSingle(id).map { it.toEntity() }

        override fun getRecordsCount(): Single<Int> =
            totalMoneyDao.getRecordCount()

    }

}