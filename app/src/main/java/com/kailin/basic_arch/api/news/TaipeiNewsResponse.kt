package com.kailin.basic_arch.api.news

import com.kailin.basic_arch.model.news.TaipeiNews

data class TaipeiNewsResponse(
    val News: List<TaipeiNews>,
    val updateTime: String
) {
    override fun toString(): String {
        return "TaipeiNews(News=$News, updateTime='$updateTime')"
    }
}