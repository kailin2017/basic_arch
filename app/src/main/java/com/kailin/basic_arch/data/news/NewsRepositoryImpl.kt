package com.kailin.basic_arch.data.news

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import com.kailin.basic_arch.api.news.TaipeiNewsResponse
import com.kailin.basic_arch.data.RepoResult
import com.kailin.basic_arch.data.login.UserInfoDataSource
import com.kailin.basic_arch.model.user.UserInfo
import javax.inject.Inject

class NewsRepositoryImpl @Inject constructor(
    private val taipeiNewsDataSource: TaipeiNewsDataSource,
) : NewsRepository {

    private val observerNewsRepo = MediatorLiveData<RepoResult<TaipeiNewsResponse>>().also { liveData->
        liveData.addSource(taipeiNewsDataSource.observerNews()){
            liveData.value = it
        }
    }

    override fun observerNews(): LiveData<RepoResult<TaipeiNewsResponse>> = observerNewsRepo

    override suspend fun fetchNews() {
        taipeiNewsDataSource.fetchNews()
    }

    override fun userInfo(): LiveData<UserInfo> = UserInfoDataSource.userInfo
}