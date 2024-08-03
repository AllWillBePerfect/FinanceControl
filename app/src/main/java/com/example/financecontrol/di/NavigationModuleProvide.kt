package com.example.financecontrol.di

import com.example.financecontrol.navigation.ActivityRequired
import com.example.financecontrol.navigation.NavigateRouter
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dagger.multibindings.ElementsIntoSet
import dagger.multibindings.IntoSet

@Module
@InstallIn(SingletonComponent::class)
class ModuleProvides {

    @Provides
    @ElementsIntoSet
    fun provideActivityRequiredSet(
    ): Set<@JvmSuppressWildcards ActivityRequired> = hashSetOf<ActivityRequired>()

    @Provides
    @IntoSet
    fun provideRouterAsActivityRequired(
        router: NavigateRouter,
    ): ActivityRequired {
        return router
    }
}