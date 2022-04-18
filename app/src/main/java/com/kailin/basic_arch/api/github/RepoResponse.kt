package com.kailin.basic_arch.api.github

import com.kailin.basic_arch.model.github.Repo

data class RepoResponse(
    val incomplete_results: Boolean = false,
    val items: MutableList<Repo> = ArrayList(),
    val total_count: Int = 0,
)