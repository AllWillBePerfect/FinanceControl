package com.example.financecontrol

import android.app.Application
import com.example.data.usecases.CheckTotalMoneyUseCase
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber
import javax.inject.Inject

@HiltAndroidApp
class App : Application() {

    @Inject
    lateinit var checkTotalMoneyUseCase: CheckTotalMoneyUseCase

    override fun onCreate() {
        super.onCreate()

        checkTotalMoneyUseCase.execute()

        if (BuildConfig.DEBUG) {}
            Timber.plant(Timber.DebugTree())
    }
}