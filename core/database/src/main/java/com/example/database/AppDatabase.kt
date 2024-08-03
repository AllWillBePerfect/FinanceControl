package com.example.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.database.dao.ExpensesDao
import com.example.database.dao.TotalMoneyDao
import com.example.database.entities.ExpensesDbo
import com.example.database.entities.MoneyTotalDbo

@Database(entities = [ExpensesDbo::class, MoneyTotalDbo::class], version = 2, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

    abstract fun expensesDao(): ExpensesDao
    abstract fun totalMoneyDao(): TotalMoneyDao

    companion object {

        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase = INSTANCE ?: synchronized(this) {
            val instance = Room.databaseBuilder(
                context.applicationContext,
                AppDatabase::class.java,
                DATABASE_NAME
            ).fallbackToDestructiveMigration()
                .build()
            INSTANCE = instance
            instance
        }

        private const val DATABASE_NAME = "FinanceControl_db"
    }
}