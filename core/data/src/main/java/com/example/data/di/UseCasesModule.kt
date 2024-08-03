package com.example.data.di

import com.example.data.usecases.CheckTotalMoneyUseCase
import com.example.data.usecases.CreateRecordUseCase
import com.example.data.usecases.DeleteRecordByIdUseCase
import com.example.data.usecases.GetAllRecordsUseCase
import com.example.data.usecases.GetTotalMoneyUseCase
import com.example.data.usecases.UpdateTotalMoneyUseCase
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface UseCasesModule {

    @Singleton
    @Binds
    fun bindCreateRecordUseCase(impl: CreateRecordUseCase.Impl): CreateRecordUseCase

    @Singleton
    @Binds
    fun bindDeleteRecordByIdUseCase(impl: DeleteRecordByIdUseCase.Impl): DeleteRecordByIdUseCase

    @Singleton
    @Binds
    fun bindGetAllRecordsUseCase(impl: GetAllRecordsUseCase.Impl): GetAllRecordsUseCase

    @Singleton
    @Binds
    fun bindGetTotalMoneyUseCase(impl: GetTotalMoneyUseCase.Impl): GetTotalMoneyUseCase

    @Singleton
    @Binds
    fun bindUpdateTotalMoneyUseCase(impl: UpdateTotalMoneyUseCase.Impl): UpdateTotalMoneyUseCase

    @Singleton
    @Binds
    fun bindCheckTotalMoneyUseCase(impl: CheckTotalMoneyUseCase.Impl): CheckTotalMoneyUseCase
}