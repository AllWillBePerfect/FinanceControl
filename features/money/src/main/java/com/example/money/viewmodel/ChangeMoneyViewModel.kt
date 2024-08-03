package com.example.money.viewmodel

import android.util.Log
import androidx.lifecycle.LifecycleOwner
import com.example.data.usecases.UpdateTotalMoneyUseCase
import com.example.ui.BaseViewModel
import com.example.ui.livedata.ErrorHolder
import com.example.ui.livedata.SingleEventObserver
import com.example.ui.livedata.SingleEventStatefulLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.addTo
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

@HiltViewModel
class ChangeMoneyViewModel @Inject constructor(
    private val updateTotalMoneyUseCase: UpdateTotalMoneyUseCase
) : BaseViewModel() {

    private val changeMoneyLiveData = SingleEventStatefulLiveData<Unit>()

    fun observeSuccessChangeMoneyLiveData(
        lifecycleOwner: LifecycleOwner,
        observer: SingleEventObserver<Unit>
    ) = changeMoneyLiveData.observeSuccessLiveData(lifecycleOwner, observer)

    fun observeErrorChangeMoneyLiveData(
        lifecycleOwner: LifecycleOwner,
        observer: SingleEventObserver<ErrorHolder>
    ) = changeMoneyLiveData.observeErrorLiveData(lifecycleOwner, observer)

    fun observeLoadingChangeMoneyLiveData(
        lifecycleOwner: LifecycleOwner,
        observer: SingleEventObserver<Boolean>
    ) = changeMoneyLiveData.observeLoadingLiveData(lifecycleOwner, observer)

    fun changeMoneyTotal(value: String) {

        if (value.isBlank()) {
            val exception = RuntimeException("Пустое значение")
            changeMoneyLiveData.setValueErrorLiveData(
                ErrorHolder(
                    error = exception,
                    message = exception.message.toString()
                )
            )
            return
        }

        updateTotalMoneyUseCase.execute(value.toDouble())
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())

            .subscribeBy(
                onComplete = {
                    Log.d("ChangeMoneyViewModel onComplete", "complete")
                    changeMoneyLiveData.setValueSuccessLiveData(Unit)
                },
                onError = { throwable ->
                    Log.d("ChangeMoneyViewModel onError", "$throwable: ${throwable.message}")
                    changeMoneyLiveData.setValueErrorLiveData(ErrorHolder(
                        error = throwable,
                        message = throwable.message.toString()
                    ))
                }
            ).addTo(disposables)
    }
}