package com.kailin.basic_arch.widget

import androidx.annotation.IdRes
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.navigation.NavDirections
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.google.android.material.textfield.TextInputLayout

fun TextInputLayout.setError(@StringRes res: Int) {
    errorIconDrawable = null
    error = try {
        context.getText(res)
    } catch (e: Exception) {
        e.printStackTrace()
        null
    }
}

fun Fragment.navigation(@IdRes resId: Int) {
    findNavController().navigate(resId)
}

fun Fragment.navigation(directions: NavDirections) {
    findNavController().navigate(directions.actionId, directions.arguments, null)
}

fun Fragment.setActionBar(toolbar: Toolbar) {
    if (requireActivity() is AppCompatActivity) {
        val activity = requireActivity() as AppCompatActivity
        activity.setSupportActionBar(toolbar)
        setHasOptionsMenu(true)
    }
}

fun Fragment.removeActionBar(){
    if (requireActivity() is AppCompatActivity) {
        val activity = requireActivity() as AppCompatActivity
        activity.setSupportActionBar(null)
    }
}

fun Fragment.navigationPop() {
    findNavController().popBackStack()
}

