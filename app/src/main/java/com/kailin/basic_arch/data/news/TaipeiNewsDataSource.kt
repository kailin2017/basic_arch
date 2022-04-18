package com.kailin.basic_arch.data.news

import androidx.lifecycle.LiveData
import com.kailin.basic_arch.api.news.TaipeiNewsResponse
import com.kailin.basic_arch.data.RepoResult

interface TaipeiNewsDataSource {

    fun observerNews(): LiveData<RepoResult<TaipeiNewsResponse>>

    suspend fun fetchNews()
}