package com.kailin.basic_arch.ui.github

import android.view.KeyEvent
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.kailin.basic_arch.BR
import com.kailin.basic_arch.R
import com.kailin.basic_arch.app.DataBindingConfig
import com.kailin.basic_arch.app.DataBindingFragment
import com.kailin.basic_arch.databinding.FragmentGithubBinding
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.distinctUntilChangedBy
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.launch

class GithubFragment : DataBindingFragment() {

    private val viewModel: GithubViewModel by viewModels()
    private val pagingAdapter = GithubPagingAdapter()
    private val eventProxy = object : GithubEventProxy() {
        override fun onSearchEditorAction(var1: TextView, var2: Int, var3: KeyEvent?): Boolean {
            return if (var2 == EditorInfo.IME_ACTION_SEARCH) {
                searchRepo()
                true
            } else {
                false
            }
        }
    }
    private lateinit var searchJob: Job

    override fun onCreateDataBindingConfig(): DataBindingConfig {
        val layoutManager = LinearLayoutManager(requireContext())
        val itemDecoration = DividerItemDecoration(requireContext(), layoutManager.orientation)
        val adapter = pagingAdapter.withLoadStateHeaderAndFooter(
            header = GithubLoadStateAdapter { pagingAdapter.retry() },
            footer = GithubLoadStateAdapter { pagingAdapter.retry() }
        )
        return DataBindingConfig(R.layout.fragment_github)
            .setDataStateViewModel(BR.viewModel, viewModel)
            .addParam(BR.eventProxy, eventProxy)
            .addParam(BR.layoutManager, layoutManager)
            .addParam(BR.adapter, adapter)
            .addParam(BR.itemDecoration, itemDecoration)
    }

    override fun onViewInit(binding: ViewDataBinding) {
        super.onViewInit(binding)
        if (binding !is FragmentGithubBinding) {
            return
        }
        lifecycleScope.launch {
            pagingAdapter.loadStateFlow
                .distinctUntilChangedBy { it.refresh }
                .filter { it.refresh is LoadState.Loading }
                .collect { binding.recyclerView.scrollToPosition(0) }
        }
    }

    private fun searchRepo() {
        if (::searchJob.isInitialized) {
            searchJob.cancel()
        }
        searchJob = lifecycleScope.launch {
            viewModel.searchRepo()?.collect {
                pagingAdapter.submitData(it)
            }
        }
    }
}