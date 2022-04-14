package com.kailin.basic_arch.ui.news

import com.kailin.basic_arch.data.news.TaipeiNewsItem
import com.kailin.basic_arch.databinding.ItemNewsBinding
import com.kailin.basic_arch.widget.recyclerView.MyDataAdapter
import com.kailin.basic_arch.widget.recyclerView.ViewHolder

class NewsAdapter(callback: (TaipeiNewsItem, Int) -> Unit) :
    MyDataAdapter<ItemNewsBinding, TaipeiNewsItem>(callback) {

    override val viewDataBindingClass: Class<ItemNewsBinding>
        get() = ItemNewsBinding::class.java

    override fun onBindViewHolder(holder: ViewHolder<ItemNewsBinding>, position: Int) {
        super.onBindViewHolder(holder, position)
        holder.binding.newsItem = data[position]
    }
}