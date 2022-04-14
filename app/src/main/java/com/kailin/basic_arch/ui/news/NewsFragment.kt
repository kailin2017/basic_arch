package com.kailin.basic_arch.ui.news

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.kailin.basic_arch.BR
import com.kailin.basic_arch.R
import com.kailin.basic_arch.app.DataBindingConfig
import com.kailin.basic_arch.app.DataBindingFragment
import com.kailin.basic_arch.utils.ActionViewHelper


class NewsFragment : DataBindingFragment(),
    Toolbar.OnMenuItemClickListener {

    private val viewModel: NewsViewModel by viewModels()
    private val adapter: NewsAdapter by lazy {
        NewsAdapter { item, _ ->
            ActionViewHelper.actionView(requireContext(), item.url)
        }
    }

    override fun onCreateDataBindingConfig(): DataBindingConfig {
        val layoutManager = LinearLayoutManager(requireContext())
        val itemDecoration = DividerItemDecoration(requireContext(), layoutManager.orientation)
        return DataBindingConfig(R.layout.fragment_news)
            .setDataStateViewModel(BR.viewModel, viewModel)
            .addParam(BR.layoutManager, layoutManager)
            .addParam(BR.adapter, adapter)
            .addParam(BR.itemDecoration, itemDecoration)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.fetchTaipeiNews()
        viewModel.taipeiNews.observe(viewLifecycleOwner) {
            adapter.updateData(it)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu_home, menu)
        viewModel.timeZone.observe(viewLifecycleOwner) {
            menu.findItem(R.id.menu_setting).title = it
        }
    }

    override fun onMenuItemClick(item: MenuItem): Boolean {
        if (item.itemId == R.id.menu_setting) {
            return true
        }
        return false
    }
}