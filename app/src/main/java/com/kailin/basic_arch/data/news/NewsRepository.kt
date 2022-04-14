package com.kailin.basic_arch.data.news

import androidx.lifecycle.LiveData
import com.kailin.basic_arch.data.RepoResult
import com.kailin.basic_arch.data.login.UserInfo

interface NewsRepository {

    fun observerNews(): LiveData<RepoResult<TaipeiNews>>

    suspend fun fetchNews()

    fun userInfo(): LiveData<UserInfo>
}