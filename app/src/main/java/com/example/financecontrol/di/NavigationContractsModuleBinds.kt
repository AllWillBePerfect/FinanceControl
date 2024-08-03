package com.example.financecontrol.di

import com.example.finance.FinanceFragmentContract
import com.example.financecontrol.navigation.finance.FinanceFragmentContractImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface NavigationContractsModuleBinds {

    @Binds
    fun bindFinanceFragmentContract(fragmentContractImpl: FinanceFragmentContractImpl): FinanceFragmentContract
}