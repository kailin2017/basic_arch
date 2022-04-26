package com.kailin.basic_arch.data.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.kailin.basic_arch.api.login.LoginRequest
import com.kailin.basic_arch.api.login.LoginService
import com.kailin.basic_arch.data.RepoResult
import com.kailin.basic_arch.data.RepoErrorData
import com.kailin.basic_arch.model.user.UserInfo
import com.kailin.basic_arch.utils.connect.toData
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import javax.inject.Inject

class LoginDataSourceImpl @Inject constructor(
    private val loginService: LoginService,
) : LoginDataSource {

    private val dispatcher: CoroutineDispatcher = Dispatchers.IO

    private val loginRespData = MutableLiveData<RepoResult<UserInfo>>()

    override fun observerLogin(): LiveData<RepoResult<UserInfo>> = loginRespData

    override suspend fun login(username: String, password: String) = withContext(dispatcher) {
        try {
            loginRespData.postValue(RepoResult.Loading)
            val response = loginService.login(LoginRequest(username, password), UserInfoDataSource.getHeaderMap())
            loginRespData.postValue(
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
            loginRespData.postValue(RepoResult.Error(e))
        }
    }
}