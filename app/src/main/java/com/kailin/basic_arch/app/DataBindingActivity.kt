package com.kailin.basic_arch.app

import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import androidx.core.util.keyIterator
import androidx.core.view.allViews
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.kailin.basic_arch.utils.ViewModelHelper.createViewModel
import com.kailin.basic_arch.widget.DialogHelper
import java.lang.reflect.Method

abstract class DataBindingActivity : AppCompatActivity() {

    private var viewDataBinding: ViewDataBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val config = onCreateDataBindingConfig()
        config.getDataStateViewModel()?.let { onCreateStateObserver(it) }
        viewDataBinding =
            DataBindingUtil.setContentView<ViewDataBinding>(this, config.layoutId)
                .also {
                    it.lifecycleOwner = this
                    val bindingParams = config.bindingParams
                    bindingParams.keyIterator().forEach { key ->
                        it.setVariable(key, bindingParams[key])
                    }
                }
        viewDataBinding?.let { onViewInit(it) }
    }

    abstract fun onCreateDataBindingConfig(): DataBindingConfig

    protected open fun onCreateStateObserver(viewModel: DataStateViewModel) {
        viewModel.message.observe(this) {
            DialogHelper.msgDialog(this, msg = it)
        }
    }

    abstract fun onViewInit(binding: ViewDataBinding)

    override fun onDestroy() {
        super.onDestroy()
        viewDataBinding?.let {
            it.root.allViews.iterator().forEachRemaining { v ->
                if (v is RecyclerView) {
                    v.adapter = null
                }
            }
            it.unbind()
        }
        viewDataBinding = null
    }
}