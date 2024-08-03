package com.example.data.di

import com.example.data.repositories.ExpensesRepository
import com.example.data.repositories.TotalMoneyRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface RepositoriesModule {

    @Singleton
    @Binds
    fun bindExpensesRepository(impl: ExpensesRepository.Impl): ExpensesRepository

    @Singleton
    @Binds
    fun bindTotalMoneyRepository(impl: TotalMoneyRepository.Impl): TotalMoneyRepository
}