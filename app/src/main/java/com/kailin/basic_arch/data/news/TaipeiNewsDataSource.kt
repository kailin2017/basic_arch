package com.kailin.basic_arch.data.news

import androidx.lifecycle.LiveData
import com.kailin.basic_arch.data.RepoResult

interface TaipeiNewsDataSource {

    fun observerNews(): LiveData<RepoResult<TaipeiNews>>

    suspend fun fetchNews()
}