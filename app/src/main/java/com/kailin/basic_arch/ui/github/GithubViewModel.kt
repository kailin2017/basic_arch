package com.kailin.basic_arch.ui.github

import androidx.lifecycle.*
import com.kailin.basic_arch.app.DataStateViewModel
import com.kailin.basic_arch.data.RepoResult
import com.kailin.basic_arch.data.github.*
import com.kailin.basic_arch.utils.connect.ConnectHelper
import kotlinx.coroutines.launch


class GithubViewModel : DataStateViewModel() {

    private val repository: GithubRepository by lazy {
        val service = ConnectHelper.createService(GithubService::class.java)
        val dataSource = GithubDataSourceImpl(service)
        return@lazy GithubRepositoryImpl(dataSource)
    }
    private val _keyword = MutableLiveData<String>()
    val keyword = _keyword
    val repoDataClear: LiveData<Unit> = repository.observerSearchClear()
    val repoData: LiveData<List<RepoItem>> =
        repository.observerSearch().distinctUntilChanged().switchMap { searchRepoSwitchMap(it) }


    init {
        _isLoading.addSource(repository.observerSearch().map { it is RepoResult.Loading }) {
            _isLoading.value = it
        }
    }

    fun searchRepo() {
        val keywordValue: String = _keyword.value ?: return
        viewModelScope.launch {
            repository.search(keyword = keywordValue)
        }
    }

    @Suppress("NON_EXHAUSTIVE_WHEN_STATEMENT")
    private fun searchRepoSwitchMap(repoResult: RepoResult<RepoData>): LiveData<List<RepoItem>> {
        val liveData = MutableLiveData<List<RepoItem>>()
        when (repoResult) {
            is RepoResult.Success -> {
                liveData.value = repoResult.data.items
            }
            is RepoResult.Error -> {
                exception(repoResult.exception)
            }
        }
        return liveData
    }
}