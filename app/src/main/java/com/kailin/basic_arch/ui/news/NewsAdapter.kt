package com.kailin.basic_arch.ui.news

import com.kailin.basic_arch.model.news.TaipeiNews
import com.kailin.basic_arch.databinding.ItemNewsBinding
import com.kailin.basic_arch.widget.recyclerView.MyDataAdapter
import com.kailin.basic_arch.widget.recyclerView.ViewHolder

class NewsAdapter(callback: (TaipeiNews, Int) -> Unit) :
    MyDataAdapter<ItemNewsBinding, TaipeiNews>(callback) {

    override val viewDataBindingClass: Class<ItemNewsBinding>
        get() = ItemNewsBinding::class.java

    override fun onBindViewHolder(holder: ViewHolder<ItemNewsBinding>, position: Int) {
        super.onBindViewHolder(holder, position)
        holder.binding.newsItem = data[position]
    }
}