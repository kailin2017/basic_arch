package com.kailin.basic_arch.ui.github

import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import com.kailin.basic_arch.model.github.Repo

class GithubPagingAdapter :
    PagingDataAdapter<Repo, GithubPagingViewHolder>(REPO_COMPARATOR) {

    override fun onBindViewHolder(holder: GithubPagingViewHolder, position: Int) {
        getItem(position)?.let { holder.bind(it) }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GithubPagingViewHolder {
        return GithubPagingViewHolder.create(parent)
    }

    companion object {
        private val REPO_COMPARATOR = object : DiffUtil.ItemCallback<Repo>() {
            override fun areItemsTheSame(oldItem: Repo, newItem: Repo): Boolean =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: Repo, newItem: Repo): Boolean =
                oldItem == newItem
        }
    }
}