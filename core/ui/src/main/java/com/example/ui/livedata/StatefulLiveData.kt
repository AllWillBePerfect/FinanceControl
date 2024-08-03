package com.example.ui.livedata

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer

class StatefulLiveData<T>(
) {
    private val successLiveData: LiveDataHolder<T> = LiveDataHolder()
    private val errorLiveData: LiveDataHolder<ErrorHolder> = LiveDataHolder()
    private val loadingLiveData: LiveDataHolder<Boolean> = LiveDataHolder()

    fun observeSuccessLiveData(lifecycleOwner: LifecycleOwner, observer: Observer<T>) =
        successLiveData.observe(lifecycleOwner, observer)

    fun observeErrorLiveData(lifecycleOwner: LifecycleOwner, observer: Observer<ErrorHolder>) =
        errorLiveData.observe(lifecycleOwner, observer)

    fun observeLoadingLiveData(lifecycleOwner: LifecycleOwner, observer: Observer<Boolean>) =
        loadingLiveData.observe(lifecycleOwner, observer)

    fun setValueSuccessLiveData(value: T) = successLiveData.setValue(value)

    fun setValueErrorLiveData(value: ErrorHolder) = errorLiveData.setValue(value)

    fun setValueLoadingLiveData(value: Boolean) = loadingLiveData.setValue(value)
}

class SingleEventStatefulLiveData<T> {
    private val successLiveData: SingleLiveDataHolder<T> = LiveDataHolder()
    private val errorLiveData: SingleLiveDataHolder<ErrorHolder> = LiveDataHolder()
    private val loadingLiveData: SingleLiveDataHolder<Boolean> = LiveDataHolder()

    fun observeSuccessLiveData(lifecycleOwner: LifecycleOwner, observer: SingleEventObserver<T>) =
        successLiveData.observe(lifecycleOwner, observer)

    fun observeErrorLiveData(lifecycleOwner: LifecycleOwner, observer: SingleEventObserver<ErrorHolder>) =
        errorLiveData.observe(lifecycleOwner, observer)

    fun observeLoadingLiveData(lifecycleOwner: LifecycleOwner, observer: SingleEventObserver<Boolean>) =
        loadingLiveData.observe(lifecycleOwner, observer)

    fun setValueSuccessLiveData(value: T) = successLiveData.setValue(SingleEvent(value))

    fun setValueErrorLiveData(value: ErrorHolder) = errorLiveData.setValue(SingleEvent(value))

    fun setValueLoadingLiveData(value: Boolean) = loadingLiveData.setValue(SingleEvent(value))
}

typealias SingleLiveDataHolder<T> = LiveDataHolder<SingleEvent<T>>
typealias SingleEventObserver<T> = Observer<SingleEvent<T>>
