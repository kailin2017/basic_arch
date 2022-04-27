@file:Suppress("SameParameterValue")

package com.kailin.basic_arch.di

import android.annotation.SuppressLint
import com.kailin.basic_arch.BuildConfig
import com.kailin.basic_arch.api.github.GithubService
import com.kailin.basic_arch.api.login.LoginService
import com.kailin.basic_arch.api.news.TaipeiNewsService
import com.kailin.basic_arch.data.user.UserInfoCacheDataSource
import com.kailin.basic_arch.data.user.UserInfoDataSource
import com.kailin.basic_arch.utils.GsonHelper
import com.kailin.basic_arch.utils.connect.interceptor.AuthInterceptor
import com.kailin.basic_arch.utils.connect.interceptor.DefaultInterceptor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.Dispatchers
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.security.SecureRandom
import java.security.cert.X509Certificate
import java.util.concurrent.TimeUnit
import javax.inject.Qualifier
import javax.inject.Singleton
import javax.net.ssl.SSLContext
import javax.net.ssl.X509TrustManager

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Qualifier
    @Retention(AnnotationRetention.RUNTIME)
    annotation class AuthOkHttpClient

    @Qualifier
    @Retention(AnnotationRetention.RUNTIME)
    annotation class DefaultOkHttpClient

    @Singleton
    @AuthOkHttpClient
    @Provides
    fun providesAuthOkHttpClient(): OkHttpClient = createOkHttp(AuthInterceptor())

    @Singleton
    @DefaultOkHttpClient
    @Provides
    fun providesDefaultOkHttpClient(): OkHttpClient = createOkHttp(DefaultInterceptor())

    private fun createOkHttp(interceptor: Interceptor): OkHttpClient {
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

    @Qualifier
    @Retention(AnnotationRetention.RUNTIME)
    annotation class AuthRetrofit

    @Qualifier
    @Retention(AnnotationRetention.RUNTIME)
    annotation class DefaultRetrofit

    @Singleton
    @AuthRetrofit
    @Provides
    fun providesAuthRetrofit(@AuthOkHttpClient okHttpClient: OkHttpClient): Retrofit =
        createRetrofit(okHttpClient, BuildConfig.OKHTTP_BASEURL)

    @Singleton
    @DefaultRetrofit
    @Provides
    fun providesDefaultRetrofit(@DefaultOkHttpClient okHttpClient: OkHttpClient): Retrofit =
        createRetrofit(okHttpClient, BuildConfig.OKHTTP_BASEURL)

    private fun createRetrofit(okHttpClient: OkHttpClient, baseUrl: String): Retrofit {
        return Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create(GsonHelper.gson))
            .build()
    }

    @Singleton
    @Provides
    fun provideUserInfoDataSource(): UserInfoDataSource = UserInfoCacheDataSource

    @Singleton
    @Provides
    fun provideIoDispatcher() = Dispatchers.IO

    @Singleton
    @Provides
    fun provideGithubService(@DefaultRetrofit retrofit: Retrofit): GithubService =
        retrofit.create(GithubService::class.java)

    @Singleton
    @Provides
    fun provideLoginService(@DefaultRetrofit retrofit: Retrofit): LoginService =
        retrofit.create(LoginService::class.java)

    @Singleton
    @Provides
    fun provideTaipeiNewsService(@DefaultRetrofit retrofit: Retrofit): TaipeiNewsService =
        retrofit.create(TaipeiNewsService::class.java)
}