package com.kailin.basic_arch.data.news

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.kailin.basic_arch.api.news.TaipeiNewsResponse
import com.kailin.basic_arch.api.news.TaipeiNewsService
import com.kailin.basic_arch.data.RepoResult
import com.kailin.basic_arch.data.RepoErrorData
import com.kailin.basic_arch.utils.connect.toData
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException

class TaipeiNewsDataSourceImpl(
    private val service: TaipeiNewsService,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) : TaipeiNewsDataSource {

    private val observerRepo = MutableLiveData<RepoResult<TaipeiNewsResponse>>()

    override fun observerNews(): LiveData<RepoResult<TaipeiNewsResponse>> = observerRepo

    override suspend fun fetchNews() = withContext(dispatcher) {
        try {
            observerRepo.postValue(RepoResult.Loading)
            val response = service.fetchNews()
            observerRepo.postValue(
                if (response.isSuccessful && response.body() != null) {
                    RepoResult.Success(response.body()!!)
                } else if (response.errorBody() != null) {
                    val data =
                        response.errorBody()?.toData<RepoErrorData>(RepoErrorData::class.java)
                    RepoResult.Error(HttpException(response), data)
                } else {
                    RepoResult.Error(HttpException(response))
                }
            )
        } catch (e: Exception) {
            e.printStackTrace()
            observerRepo.postValue(RepoResult.Error(e))
        }
    }
}