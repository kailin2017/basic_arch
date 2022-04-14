package com.kailin.basic_arch.ui.login

import android.view.KeyEvent
import android.widget.TextView

interface LoginEventProxy {

    fun onClickLogin()

    fun onClickDevLogin()

    fun onLoginEditorAction(var1: TextView, var2: Int, var3: KeyEvent?): Boolean
}