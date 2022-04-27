package com.kailin.basic_arch.data.login

import androidx.lifecycle.LiveData
import com.kailin.basic_arch.data.RepoResult
import com.kailin.basic_arch.model.user.UserInfo

interface LoginDataSource {

    fun observerLogin(): LiveData<RepoResult<UserInfo>>

    suspend fun login(username: String, password: String, headerMap: HashMap<String, String>)
}