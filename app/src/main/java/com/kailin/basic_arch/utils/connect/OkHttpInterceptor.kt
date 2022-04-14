package com.kailin.basic_arch.utils.connect

import okhttp3.Interceptor
import okhttp3.Response

class OkHttpInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
//        val url = chain.request().url
//        val domain = "${url.scheme}://${url.host}"
        val request = chain.request().newBuilder().run {
            build()
        }
        return chain.proceed(request)
    }
}