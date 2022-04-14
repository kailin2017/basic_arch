package com.kailin.basic_arch.data.news

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import com.kailin.basic_arch.data.RepoResult
import com.kailin.basic_arch.data.login.UserInfoDataSource
import com.kailin.basic_arch.data.login.UserInfo

class NewsRepositoryImpl(
    private val taipeiNewsDataSource: TaipeiNewsDataSource,
) : NewsRepository {

    private val observerNewsRepo = MediatorLiveData<RepoResult<TaipeiNews>>().also { liveData->
        liveData.addSource(taipeiNewsDataSource.observerNews()){
            liveData.value = it
        }
    }

    override fun observerNews(): LiveData<RepoResult<TaipeiNews>> = observerNewsRepo

    override suspend fun fetchNews() {
        taipeiNewsDataSource.fetchNews()
    }

    override fun userInfo(): LiveData<UserInfo> = UserInfoDataSource.userInfo
}