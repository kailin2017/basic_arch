package com.kailin.basic_arch.di

import com.kailin.basic_arch.BuildConfig
import com.kailin.basic_arch.api.github.GithubService
import com.kailin.basic_arch.api.login.LoginService
import com.kailin.basic_arch.api.news.TaipeiNewsService
import com.kailin.basic_arch.data.login.UserInfoDataSource
import com.kailin.basic_arch.utils.connect.ConnectHelper
import com.kailin.basic_arch.utils.connect.interceptor.AuthInterceptor
import com.kailin.basic_arch.utils.connect.interceptor.DefaultInterceptor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.Dispatchers
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import javax.inject.Qualifier
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Qualifier
    @Retention(AnnotationRetention.RUNTIME)
    annotation class AuthOkHttpClient

    @Qualifier
    @Retention(AnnotationRetention.RUNTIME)
    annotation class AuthRetrofit

    @Qualifier
    @Retention(AnnotationRetention.RUNTIME)
    annotation class DefaultOkHttpClient

    @Qualifier
    @Retention(AnnotationRetention.RUNTIME)
    annotation class DefaultRetrofit

    @Singleton
    @AuthOkHttpClient
    @Provides
    fun authOkHttp(): OkHttpClient = ConnectHelper.createOkHttp(AuthInterceptor())

    @Singleton
    @AuthRetrofit
    @Provides
    fun authRetrofit(@AuthOkHttpClient okHttpClient: OkHttpClient): Retrofit =
        ConnectHelper.createRetrofit(okHttpClient, BuildConfig.OKHTTP_BASEURL)

    @Singleton
    @DefaultOkHttpClient
    @Provides
    fun defaultOkHttp(): OkHttpClient = ConnectHelper.createOkHttp(DefaultInterceptor())

    @Singleton
    @DefaultRetrofit
    @Provides
    fun defaultRetrofit(@DefaultOkHttpClient okHttpClient: OkHttpClient): Retrofit =
        ConnectHelper.createRetrofit(okHttpClient, BuildConfig.OKHTTP_BASEURL)

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