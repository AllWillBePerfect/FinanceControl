package com.example.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.database.entities.MoneyTotalDbo
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single

@Dao
interface TotalMoneyDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertRecord(totalMoneyDbo: MoneyTotalDbo): Completable

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun updateRecord(totalMoneyDbo: MoneyTotalDbo): Completable

    @Query("Select * from money_total_dbo where id = :id")
    fun findRecord(id: Long): Flowable<MoneyTotalDbo>

    @Query("Select * from money_total_dbo where id = :id")
    fun findRecordSingle(id: Long): Single<MoneyTotalDbo>

    @Query("select count(*) from money_total_dbo")
    fun getRecordCount(): Single<Int>
}