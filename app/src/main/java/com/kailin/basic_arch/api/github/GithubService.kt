package com.kailin.basic_arch.api.github

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface GithubService {

    @GET("https://api.github.com/search/repositories")
    suspend fun search(
        @Query("q") searchKeyword: String,
        @Query("page") page: Int = 1,
        @Query("per_page") per_page: Int = 5,
    ): Response<RepoResponse>
}