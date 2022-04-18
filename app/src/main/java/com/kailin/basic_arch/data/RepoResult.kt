package com.kailin.basic_arch.data

sealed class RepoResult<out R> {

    data class Success<out T>(val data: T) : RepoResult<T>()
    data class Error(val exception: Exception, val data: RepoErrorData? = null) :
        RepoResult<Nothing>()

    object Loading : RepoResult<Nothing>()

    override fun toString(): String {
        return when (this) {
            is Success<*> -> "Success[data=$data]"
            is Error -> "Error[exception=$exception]"
            Loading -> "Loading"
        }
    }
}

val RepoResult<*>.succeeded
    get() = this is RepoResult.Success && data != null