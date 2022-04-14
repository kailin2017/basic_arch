package com.kailin.basic_arch.ui.github

import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.kailin.basic_arch.BR
import com.kailin.basic_arch.R
import com.kailin.basic_arch.app.DataBindingConfig
import com.kailin.basic_arch.app.DataBindingFragment

class GithubFragment : DataBindingFragment() {

    private val viewModel: GithubViewModel by viewModels()
    private val adapter = GithubAdapter()
    private val eventProxy = object : GithubEventProxy() {
        override fun onSearchEditorAction(var1: TextView, var2: Int, var3: KeyEvent?): Boolean {
            return if (var2 == EditorInfo.IME_ACTION_SEARCH) {
                searchRepo()
                true
            } else {
                false
            }
        }

        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            super.onScrolled(recyclerView, dx, dy)
            if (viewModel.isLoading.value == true) {
                return
            }
            if (recyclerView.layoutManager !is LinearLayoutManager) {
                return
            }
            if ((recyclerView.layoutManager as LinearLayoutManager).findLastCompletelyVisibleItemPosition() > adapter.itemCount - 3) {
                searchRepo()
            }
        }
    }

    override fun onCreateDataBindingConfig(): DataBindingConfig {
        val layoutManager = LinearLayoutManager(requireContext())
        val itemDecoration = DividerItemDecoration(requireContext(), layoutManager.orientation)
        return DataBindingConfig(R.layout.fragment_github)
            .setDataStateViewModel(BR.viewModel, viewModel)
            .addParam(BR.eventProxy, eventProxy)
            .addParam(BR.layoutManager, layoutManager)
            .addParam(BR.adapter, adapter)
            .addParam(BR.itemDecoration, itemDecoration)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.repoDataClear.observe(viewLifecycleOwner) {
            adapter.clearData()
        }
        viewModel.repoData.observe(viewLifecycleOwner) {
            adapter.appendData(it)
        }
    }

    private fun searchRepo() {
        viewModel.searchRepo()
    }
}