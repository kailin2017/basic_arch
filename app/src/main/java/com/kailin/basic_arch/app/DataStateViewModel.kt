package com.kailin.basic_arch.app

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel

abstract class DataStateViewModel : ViewModel() {

    protected val _message = MediatorLiveData<String>()
    val message: LiveData<String> = _message

    protected val _isLoading = MediatorLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    init {
        setLoading(false)
    }

    fun setThrowable(e: Throwable) {
        e.message?.let { setMessage(it) }
    }

    fun setLoading(isLoading: Boolean) {
        _isLoading.value = isLoading
    }

    fun setMessage(message: String) {
        _message.postValue(message)
    }
}
