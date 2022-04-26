package com.kailin.basic_arch.ui.login

import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.viewModels
import com.kailin.basic_arch.BR
import com.kailin.basic_arch.R
import com.kailin.basic_arch.app.DataBindingConfig
import com.kailin.basic_arch.app.DataBindingFragment
import com.kailin.basic_arch.widget.ToastHelper
import com.kailin.basic_arch.widget.navigationPop
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginFragment : DataBindingFragment(), LoginEventProxy {

    private val viewModel: LoginViewModel by viewModels()

    override fun onCreateDataBindingConfig(): DataBindingConfig {
        return DataBindingConfig(R.layout.fragment_login)
            .setDataStateViewModel(BR.viewModel, viewModel)
            .addParam(BR.eventProxy, this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.loginFinish.observe(viewLifecycleOwner) {
            ToastHelper.showShort(getString(R.string.login_login_success))
            navigationPop()
        }
    }

    override fun onClickLogin() {
        viewModel.login()
    }

    override fun onClickDevLogin() {
        viewModel.devLogin()
    }

    override fun onLoginEditorAction(var1: TextView, var2: Int, var3: KeyEvent?): Boolean {
        return if (var2 == EditorInfo.IME_ACTION_SEND) {
            onClickLogin()
            true
        } else {
            false
        }
    }
}