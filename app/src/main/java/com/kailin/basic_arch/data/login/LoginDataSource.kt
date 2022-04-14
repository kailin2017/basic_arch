package com.kailin.basic_arch.data.login

import androidx.lifecycle.LiveData
import com.kailin.basic_arch.data.RepoResult

interface LoginDataSource {

    fun observerLogin(): LiveData<RepoResult<UserInfo>>

    suspend fun login(username: String, password: String)
}