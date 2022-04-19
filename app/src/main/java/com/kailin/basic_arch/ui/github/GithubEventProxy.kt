package com.kailin.basic_arch.ui.github

import android.view.KeyEvent
import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

abstract class GithubEventProxy : RecyclerView.OnScrollListener() {

    abstract fun onSearchEditorAction(var1: TextView, var2: Int, var3: KeyEvent?): Boolean

    abstract fun onKey(var1: View, var2: Int, var3: KeyEvent?): Boolean

    abstract fun onRetry()
}