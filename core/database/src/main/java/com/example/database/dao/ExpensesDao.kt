package com.example.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.database.entities.ExpensesDbo
import com.example.database.entities.ExpensesDbo.Companion.TABLE_NAME
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single

@Dao
interface ExpensesDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertRecord(expensesDbo: ExpensesDbo): Single<Long>

    @Delete
    fun deleteRecord(expensesDbo: ExpensesDbo): Completable

    @Query("DELETE FROM $TABLE_NAME WHERE id = :id")
    fun deleteRecordById(id: Long): Completable

    @Query("SELECT * FROM $TABLE_NAME")
    fun getAllRecords(): Flowable<List<ExpensesDbo>>

    @Query("SELECT * FROM $TABLE_NAME WHERE id = :id")
    fun getRecordById(id: Long): Single<ExpensesDbo>
}