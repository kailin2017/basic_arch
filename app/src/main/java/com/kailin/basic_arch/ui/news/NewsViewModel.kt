package com.kailin.basic_arch.ui.news

import androidx.lifecycle.*
import com.kailin.basic_arch.api.news.TaipeiNewsResponse
import com.kailin.basic_arch.app.DataStateViewModel
import com.kailin.basic_arch.data.RepoResult
import com.kailin.basic_arch.data.news.NewsRepository
import com.kailin.basic_arch.model.news.TaipeiNews
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NewsViewModel @Inject constructor(
    private val repository: NewsRepository
) : DataStateViewModel() {

    val timeZone: LiveData<String> = repository.userInfo().map { it.timeZone }

    val taipeiNews: LiveData<List<TaipeiNews>> =
        repository.observerNews().switchMap { filterTaipeiNews(it) }

    private val _isEmpty = MutableLiveData(false)

    val isEmpty: LiveData<Boolean> = _isEmpty

    init {
        _isLoading.addSource(repository.observerNews().map { it is RepoResult.Loading }) {
            _isLoading.value = it
        }
    }

    fun fetchTaipeiNews() {
        viewModelScope.launch {
            repository.fetchNews()
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