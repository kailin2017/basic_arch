package com.kailin.basic_arch.ui.github

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.kailin.basic_arch.databinding.ItemGithubBinding
import com.kailin.basic_arch.model.github.Repo

class GithubPagingViewHolder private constructor(private val binding: ItemGithubBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(data: Repo) {
        binding.data = data
    }

    companion object {
        fun create(parent: ViewGroup): GithubPagingViewHolder {
            val binding =
                ItemGithubBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            return GithubPagingViewHolder(binding)
        }
    }
}