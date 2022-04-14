package com.kailin.basic_arch.data.github

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.kailin.basic_arch.data.RepoResult

class GithubRepositoryImpl(
    private val dataSource: GithubDataSource
) : GithubRepository {

    private var currKeyword = ""
    private var currPage = 0
    private var maxPage = 1
    private val pageItemCount = 5

    private val _observerSearchClear = MutableLiveData<Unit>()
    private val _observerSearch = dataSource.observerSearch()

    override fun observerSearchClear(): LiveData<Unit> {
        return _observerSearchClear
    }

    override fun observerSearch(): LiveData<RepoResult<RepoData>> {
        return _observerSearch
    }

    override suspend fun search(keyword: String) {
        if (currKeyword != keyword) {
            currKeyword = keyword
            currPage = 0
            maxPage = 1
            _observerSearchClear.value = Unit
        }
        if (currPage < maxPage && currKeyword.isNotEmpty()) {
            dataSource.search(currKeyword, currPage + 1, pageItemCount)
        }
    }
}