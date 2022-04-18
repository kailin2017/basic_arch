package com.kailin.basic_arch.data.github

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.kailin.basic_arch.api.github.GithubService
import com.kailin.basic_arch.model.github.Repo
import kotlinx.coroutines.flow.Flow

class GithubPagingRepository(
    private val pageSize: Int = 50,
    private val service: GithubService
) {

    fun getPagingData(keyword: String): Flow<PagingData<Repo>> {
        return Pager(
            config = PagingConfig(pageSize),
            pagingSourceFactory = { GithubPagingSource(keyword, service) }
        ).flow
    }
}