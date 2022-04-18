package com.kailin.basic_arch.data.github

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.kailin.basic_arch.api.github.GithubService
import com.kailin.basic_arch.model.github.Repo

class GithubPagingSource(private val keyword: String, private val githubService: GithubService) :
    PagingSource<Int, Repo>() {

    override fun getRefreshKey(state: PagingState<Int, Repo>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Repo> {
        return try {
            val page = params.key ?: 1
            val pageSize = params.loadSize
            val response = githubService.search(keyword, page, pageSize)
            val items: List<Repo> = response.body()?.items ?: ArrayList()
            val pagePrev = if (page > 1) {
                page - 1
            } else {
                null
            }
            val pageNext = if (items.isNullOrEmpty()) {
                null
            } else {
                page + 1
            }
            LoadResult.Page(items, pagePrev, pageNext)
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }
}