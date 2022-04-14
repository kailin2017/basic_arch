package com.kailin.basic_arch.ui.github

import com.kailin.basic_arch.data.github.RepoItem
import com.kailin.basic_arch.databinding.ItemGithubBinding
import com.kailin.basic_arch.widget.recyclerView.MyDataAdapter
import com.kailin.basic_arch.widget.recyclerView.ViewHolder

class GithubAdapter :MyDataAdapter<ItemGithubBinding,RepoItem>(){

    override val viewDataBindingClass: Class<ItemGithubBinding>
        get() = ItemGithubBinding::class.java

    override fun onBindViewHolder(holder: ViewHolder<ItemGithubBinding>, position: Int) {
        super.onBindViewHolder(holder, position)
        val itemData = data[position]
        holder.binding.data = itemData
    }
}