package com.example.data.di

import android.content.Context
import com.example.database.AppDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModuleProvide {

    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext context: Context) = AppDatabase.getDatabase(context)

    @Singleton
    @Provides
    fun provideExpensesDao(db: AppDatabase) = db.expensesDao()

    @Singleton
    @Provides
    fun provideTotalMoneyDao(db: AppDatabase) = db.totalMoneyDao()
}