package com.kailin.basic_arch.data.github

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.kailin.basic_arch.api.github.GithubService
import com.kailin.basic_arch.model.github.Repo
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GithubPagingRepository @Inject constructor(
    private val service: GithubService
) {

    fun getPagingData(keyword: String): Flow<PagingData<Repo>> {
        return Pager(
            config = PagingConfig(pageSize),
            pagingSourceFactory = { GithubPagingSource(keyword, service) }
        ).flow
    }

    companion object {
        private const val pageSize: Int = 50
    }
}