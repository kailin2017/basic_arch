package com.kailin.basic_arch.app

import androidx.lifecycle.*

abstract class DataStateViewModel : ViewModel() {

    protected val _message = MediatorLiveData<String>()
    val message: LiveData<String> = _message

    protected val _isLoading = MediatorLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    fun exception(e: Throwable) {
        e.printStackTrace()
        _message.postValue(e.message)
    }
}
