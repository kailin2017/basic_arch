package com.kailin.basic_arch.app

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.Toolbar
import androidx.core.util.keyIterator
import androidx.core.view.allViews
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.kailin.basic_arch.R
import com.kailin.basic_arch.widget.DialogHelper
import com.kailin.basic_arch.widget.removeActionBar
import com.kailin.basic_arch.widget.setActionBar

abstract class DataBindingFragment : Fragment() {

    private var viewDataBinding: ViewDataBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val config = onCreateDataBindingConfig()
        config.getDataStateViewModel()?.let { onCreateStateObserver(it) }
        viewDataBinding =
            DataBindingUtil.inflate<ViewDataBinding>(inflater, config.layoutId, container, false)
                .also {
                    it.lifecycleOwner = viewLifecycleOwner
                    val bindingParams = config.bindingParams
                    bindingParams.keyIterator().forEach { key ->
                        it.setVariable(key, bindingParams[key])
                    }
                }
        return viewDataBinding?.root
    }

    abstract fun onCreateDataBindingConfig(): DataBindingConfig

    protected open fun onCreateStateObserver(viewModel: DataStateViewModel) {
        viewModel.message.observe(viewLifecycleOwner) {
            DialogHelper.msgDialog(requireContext(), msg = it)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewDataBinding?.let { onViewInit(it) }
    }

    protected open fun onViewInit(binding: ViewDataBinding) {
        val toolbar: Toolbar? = binding.root.findViewById(R.id.toolbar)
        toolbar?.let { setActionBar(it) }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        viewDataBinding?.let {
            it.root.allViews.iterator().forEachRemaining { v ->
                if (v is RecyclerView) {
                    v.adapter = null
                }
            }
            it.unbind()
        }
        viewDataBinding = null
        removeActionBar()
    }

    fun setBindVariable(key: Int, value: Any?) {
        viewDataBinding?.setVariable(key, value)
    }
}