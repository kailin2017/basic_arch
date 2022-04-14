package com.kailin.basic_arch.data.github

data class RepoData(
    val incomplete_results: Boolean = false,
    val items: MutableList<RepoItem> = ArrayList(),
    val total_count: Int = 0,
) : GithubResult()