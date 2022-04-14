package com.kailin.basic_arch.data.news

data class TaipeiNews(
    val News: List<TaipeiNewsItem>,
    val updateTime: String
) {
    override fun toString(): String {
        return "TaipeiNews(News=$News, updateTime='$updateTime')"
    }
}