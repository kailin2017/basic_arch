package com.kailin.basic_arch.data.github

import androidx.lifecycle.LiveData
import com.kailin.basic_arch.data.RepoResult
import com.kailin.basic_arch.data.login.UserInfo
import retrofit2.http.Query

interface GithubDataSource {

    fun observerSearch(): LiveData<RepoResult<RepoData>>

    suspend fun search(
        keyword: String,
        page: Int,
        pageItemCount: Int,
    )
}