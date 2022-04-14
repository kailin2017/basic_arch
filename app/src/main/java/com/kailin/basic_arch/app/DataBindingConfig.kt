package com.kailin.basic_arch.app

import android.util.SparseArray

class DataBindingConfig(val layoutId: Int) {

    val bindingParams = SparseArray<Any>()

    private var viewModelKey: Int = -1

    fun getDataStateViewModel(): DataStateViewModel? {
        var viewModel: DataStateViewModel? = null
        if (bindingParams[viewModelKey] != null) {
            viewModel = bindingParams[viewModelKey] as DataStateViewModel?
        }
        return viewModel
    }

    fun setDataStateViewModel(key: Int, viewModel: DataStateViewModel): DataBindingConfig {
        viewModelKey = key
        bindingParams.put(key, viewModel)
        return this
    }

    fun addParam(key: Int, value: Any): DataBindingConfig {
        if (bindingParams[key] == null) {
            bindingParams.put(key, value)
        }
        return this
    }
}