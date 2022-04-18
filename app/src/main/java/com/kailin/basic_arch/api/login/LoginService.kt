package com.kailin.basic_arch.api.login

import com.kailin.basic_arch.model.user.UserInfo
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.HeaderMap
import retrofit2.http.POST

interface LoginService {

    @POST("/api/login")
    suspend fun login(
        @Body data: LoginRequest,
        @HeaderMap headerMap: Map<String, String>
    ): Response<UserInfo>
}