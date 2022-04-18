package com.kailin.basic_arch.data.news

import androidx.lifecycle.LiveData
import com.kailin.basic_arch.api.news.TaipeiNewsResponse
import com.kailin.basic_arch.data.RepoResult
import com.kailin.basic_arch.model.user.UserInfo

interface NewsRepository {

    fun observerNews(): LiveData<RepoResult<TaipeiNewsResponse>>

    suspend fun fetchNews()

    fun userInfo(): LiveData<UserInfo>
}