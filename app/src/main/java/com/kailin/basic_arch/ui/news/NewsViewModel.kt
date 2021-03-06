package com.kailin.basic_arch.ui.news

import androidx.lifecycle.*
import com.kailin.basic_arch.api.news.TaipeiNewsResponse
import com.kailin.basic_arch.api.news.TaipeiNewsService
import com.kailin.basic_arch.app.DataStateViewModel
import com.kailin.basic_arch.data.RepoResult
import com.kailin.basic_arch.data.news.*
import com.kailin.basic_arch.model.news.TaipeiNews
import com.kailin.basic_arch.utils.connect.ConnectHelper
import kotlinx.coroutines.launch

class NewsViewModel : DataStateViewModel() {

    private val mNewsRepository: NewsRepository by lazy {
        val service = ConnectHelper.createService(TaipeiNewsService::class.java)
        val dataSource = TaipeiNewsDataSourceImpl(service)
        NewsRepositoryImpl(dataSource)
    }

    val timeZone: LiveData<String> = mNewsRepository.userInfo().map { it.timeZone }

    val taipeiNews: LiveData<List<TaipeiNews>> =
        mNewsRepository.observerNews().switchMap { filterTaipeiNews(it) }

    private val _isEmpty = MutableLiveData(false)

    val isEmpty: LiveData<Boolean> = _isEmpty

    init {
        _isLoading.addSource(mNewsRepository.observerNews().map { it is RepoResult.Loading }) {
            _isLoading.value = it
        }
    }

    fun fetchTaipeiNews() {
        viewModelScope.launch {
            mNewsRepository.fetchNews()
        }
    }

    private fun filterTaipeiNews(result: RepoResult<TaipeiNewsResponse>): LiveData<List<TaipeiNews>> {
        val liveData = MutableLiveData<List<TaipeiNews>>()
        when (result) {
            is RepoResult.Success -> {
                val data: TaipeiNewsResponse = result.data
                if (data.News.isNullOrEmpty()) {
                    _isEmpty.value = true
                } else {
                    liveData.value = data.News.filter { it.isValid(data.updateTime) }
                }
            }
            is RepoResult.Error -> {
                _message.value = if (result.data != null) {
                    result.data.error
                } else {
                    result.exception.message
                }
            }
            else -> {}
        }
        return liveData
    }
}