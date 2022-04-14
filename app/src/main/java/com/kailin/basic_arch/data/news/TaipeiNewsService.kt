package com.kailin.basic_arch.data.news

import retrofit2.Response
import retrofit2.http.GET

interface TaipeiNewsService {

    @GET("https://tcgbusfs.blob.core.windows.net/dotapp/news.json")
    suspend fun fetchNews(): Response<TaipeiNews>
}