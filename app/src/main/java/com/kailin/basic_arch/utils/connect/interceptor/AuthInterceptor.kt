package com.kailin.basic_arch.utils.connect.interceptor

import okhttp3.Interceptor
import okhttp3.Response

class AuthInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request().newBuilder().run {
            build()
        }
        return chain.proceed(request)
    }
}