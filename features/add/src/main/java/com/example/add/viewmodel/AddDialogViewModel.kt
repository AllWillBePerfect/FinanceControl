package com.example.add.viewmodel

import android.util.Log
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import com.example.data.usecases.CreateRecordUseCase
import com.example.data.usecases.UpdateTotalMoneyUseCase
import com.example.models.ExpensesType
import com.example.ui.BaseViewModel
import com.example.ui.livedata.ErrorHolder
import com.example.ui.livedata.SingleEventObserver
import com.example.ui.livedata.SingleEventStatefulLiveData
import com.example.ui.livedata.StatefulLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.addTo
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

@HiltViewModel
class AddDialogViewModel @Inject constructor(
    private val createRecordUseCase: CreateRecordUseCase,
    private val updateTotalMoneyUseCase: UpdateTotalMoneyUseCase
) : BaseViewModel() {

    private val addRecordLiveData = SingleEventStatefulLiveData<Unit>()

    private var type = ExpensesType.SPENDING

    fun changeType(type: ExpensesType) {
        this.type = type
    }

    fun getType() = type

    fun observeSuccessAddRecordLiveData(
        lifecycleOwner: LifecycleOwner,
        observer: SingleEventObserver<Unit>
    ) = addRecordLiveData.observeSuccessLiveData(lifecycleOwner, observer)

    fun observeErrorAddRecordLiveData(
        lifecycleOwner: LifecycleOwner,
        observer: SingleEventObserver<ErrorHolder>
    ) = addRecordLiveData.observeErrorLiveData(lifecycleOwner, observer)

    fun observeLoadingAddRecordLiveData(
        lifecycleOwner: LifecycleOwner,
        observer: SingleEventObserver<Boolean>
    ) = addRecordLiveData.observeLoadingLiveData(lifecycleOwner, observer)

    fun createRecord(value: String) {
        if (value.isBlank()) {
            val exception = RuntimeException("Пустое значение")
            addRecordLiveData.setValueErrorLiveData(
                ErrorHolder(
                    error = exception,
                    message = exception.message.toString()
                )
            )
            return
        }

        createRecordUseCase.invoke(value.toDouble(), type)
            .flatMapCompletable { updateTotalMoneyUseCase.execute(value = value.toDouble(), type = type) }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
                onComplete = {
                    Log.d("AddDialogViewModel onComplete", "onComplete")
                    addRecordLiveData.setValueSuccessLiveData(Unit)
                },
                onError = { throwable ->
//                    if (throwable is RuntimeException)
                    Log.d("AddDialogViewModel error", "$throwable: ${throwable.message}")
                    addRecordLiveData.setValueErrorLiveData(
                        ErrorHolder(
                            error = throwable,
                            message = throwable.message.toString()
                        )
                    )
                }
            )
            .addTo(disposables)
    }

}