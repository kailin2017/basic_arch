package com.kailin.basic_arch.data.github

import androidx.lifecycle.LiveData
import com.kailin.basic_arch.data.RepoResult

interface GithubRepository {

    fun observerSearchClear(): LiveData<Unit>

    fun observerSearch(): LiveData<RepoResult<RepoData>>

    suspend fun search(
        keyword: String
    )
}