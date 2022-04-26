package com.kailin.basic_arch.utils.connect

import android.annotation.SuppressLint
import com.kailin.basic_arch.BuildConfig
import com.kailin.basic_arch.utils.GsonHelper
import com.kailin.basic_arch.utils.connect.interceptor.DefaultInterceptor
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.security.SecureRandom
import java.security.cert.X509Certificate
import java.util.concurrent.TimeUnit
import javax.net.ssl.SSLContext
import javax.net.ssl.X509TrustManager

object ConnectHelper {

    fun createOkHttp(interceptor: Interceptor): OkHttpClient {
        val builder = OkHttpClient.Builder()
            .addNetworkInterceptor(interceptor)
            .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
            .connectTimeout(BuildConfig.OKHTTP_CONECT_TIMEOUT, TimeUnit.SECONDS)

        if (BuildConfig.DEBUG) {
            try {
                val x509TrustManager = @SuppressLint("CustomX509TrustManager")
                object : X509TrustManager {
                    @SuppressLint("TrustAllX509TrustManager")
                    override fun checkClientTrusted(p0: Array<out X509Certificate>?, p1: String?) {
                    }

                    @SuppressLint("TrustAllX509TrustManager")
                    override fun checkServerTrusted(p0: Array<out X509Certificate>?, p1: String?) {
                    }

                    override fun getAcceptedIssuers(): Array<X509Certificate> = arrayOf()
                }
                val sslContext = SSLContext.getInstance("TLS")
                sslContext.init(null, arrayOf(x509TrustManager), SecureRandom())
                builder.sslSocketFactory(sslContext.socketFactory, x509TrustManager)
                builder.hostnameVerifier { _, _ -> true }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

        return builder.build()
    }

    fun createRetrofit(okHttpClient: OkHttpClient, baseUrl: String): Retrofit {
        return Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create(GsonHelper.gson))
            .build()
    }
}