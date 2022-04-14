package com.kailin.basic_arch.ui.main

import androidx.databinding.ViewDataBinding
import com.kailin.basic_arch.R
import com.kailin.basic_arch.app.DataBindingActivity
import com.kailin.basic_arch.app.DataBindingConfig

class MainActivity : DataBindingActivity() {

    override fun onCreateDataBindingConfig(): DataBindingConfig {
        return DataBindingConfig(R.layout.activity_main)
    }

    override fun onViewInit(binding: ViewDataBinding) {
    }
}