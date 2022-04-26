package com.kailin.basic_arch.widget

import android.os.Handler
import android.os.Looper
import android.widget.Toast
import com.kailin.basic_arch.app.App

object ToastHelper {

    private var toast: Toast? = null

    fun showLong(text: String?) = makeText(text, Toast.LENGTH_LONG)

    fun showShort(text: String?) = makeText(text, Toast.LENGTH_SHORT)

    private fun makeText(text: CharSequence?, duration: Int) {
        text ?: return
        toast?.cancel()
        toast = Toast.makeText(App.instance, text, duration).also { it.show() }
        Handler(Looper.myLooper()!!).postDelayed({
            toast?.cancel()
            toast = null
        }, 5000)
    }
}