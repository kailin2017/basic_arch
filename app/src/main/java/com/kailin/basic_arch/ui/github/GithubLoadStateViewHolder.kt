package com.kailin.basic_arch.ui.github

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.recyclerview.widget.RecyclerView
import com.kailin.basic_arch.databinding.ItemGithubStateBinding

class GithubLoadStateViewHolder private constructor(
    private val binding: ItemGithubStateBinding,
    retry: () -> Unit
) : RecyclerView.ViewHolder(binding.root) {

    init {
        binding.retry.setOnClickListener { retry.invoke() }
    }

    fun bind(loadState: LoadState) {
        if (loadState is LoadState.Error) {
            binding.errorMessage.text = loadState.error.localizedMessage
        }
        binding.progress.isVisible = loadState is LoadState.Loading
        binding.retry.isVisible = loadState is LoadState.Error
        binding.errorMessage.isVisible = loadState is LoadState.Error
    }

    companion object {

        fun create(parent: ViewGroup, retry: () -> Unit): GithubLoadStateViewHolder {
            val binding = ItemGithubStateBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
            return GithubLoadStateViewHolder(binding, retry)
        }
    }
}