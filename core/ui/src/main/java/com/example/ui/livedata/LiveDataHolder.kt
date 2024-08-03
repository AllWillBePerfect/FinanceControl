package com.example.ui.livedata

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer

class LiveDataHolder<T> {

    private val liveData: MutableLiveData<T>

    constructor() {
        liveData = MutableLiveData()
    }

    constructor(value: T) {
        liveData = MutableLiveData(value)
    }

    fun observe(lifecycleOwner: LifecycleOwner, observer: Observer<T>) =
        liveData.observe(lifecycleOwner, observer)

    fun setValue(value: T) {
        liveData.value = value
    }
}