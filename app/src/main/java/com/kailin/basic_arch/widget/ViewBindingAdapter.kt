package com.kailin.basic_arch.widget

import android.view.View
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView

@BindingAdapter(value = ["onEditorAction"], requireAll = false)
fun setOnEditorActionListener(
    view: TextView,
    listener: TextView.OnEditorActionListener?
) {
    view.setOnEditorActionListener(listener)
}

@BindingAdapter(value = ["onKey"], requireAll = false)
fun setOnKeyListener(
    view: View,
    listener: View.OnKeyListener?
) {
    view.setOnKeyListener(listener)
}

@BindingAdapter(value = ["error"], requireAll = false)
fun setOnError(
    view: EditTextInputLayout,
    error: CharSequence?
) {
    view.error = error
}

@BindingAdapter(value = ["adapter"], requireAll = false)
fun setRecyclerViewAdapter(
    view: RecyclerView,
    adapter: RecyclerView.Adapter<*>
) {
    view.adapter = adapter
}

@BindingAdapter(value = ["itemDecoration"], requireAll = false)
fun setRecyclerViewItemDecoration(
    view: RecyclerView,
    itemDecoration: RecyclerView.ItemDecoration
) {
    view.addItemDecoration(itemDecoration)
}

@BindingAdapter(value = ["onScrollListener"], requireAll = false)
fun setRecyclerViewOnScrollListener(
    view: RecyclerView,
    listener: RecyclerView.OnScrollListener
) {
    view.addOnScrollListener(listener)
}
