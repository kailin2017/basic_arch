package com.kailin.basic_arch.data.github

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.kailin.basic_arch.data.RepoResult
import com.kailin.basic_arch.data.error.ErrorRespData
import com.kailin.basic_arch.utils.connect.toData
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException

class GithubDataSourceImpl(
    private val service: GithubService,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) : GithubDataSource {

    private val repoData = MutableLiveData<RepoResult<RepoData>>()

    override fun observerSearch(): LiveData<RepoResult<RepoData>> = repoData

    override suspend fun search(keyword: String, page: Int, pageItemCount: Int) =
        withContext(dispatcher) {
            try {
                repoData.postValue(RepoResult.Loading)
                val response = service.search(keyword, page, pageItemCount)
                repoData.postValue(
                    when {
                        response.isSuccessful && response.body() != null -> {
                            RepoResult.Success(response.body()!!)
                        }
                        response.errorBody() != null -> {
                            val data = response.errorBody()
                                ?.toData<ErrorRespData>(ErrorRespData::class.java)
                            RepoResult.Error(HttpException(response), data)
                        }
                        else -> {
                            RepoResult.Error(HttpException(response))
                        }
                    }
                )
            } catch (e: Exception) {
                e.printStackTrace()
                repoData.postValue(RepoResult.Error(e))
            }
        }
}