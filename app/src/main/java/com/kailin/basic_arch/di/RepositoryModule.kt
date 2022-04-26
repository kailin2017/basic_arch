package com.kailin.basic_arch.di

import com.kailin.basic_arch.data.login.LoginDataSource
import com.kailin.basic_arch.data.login.LoginDataSourceImpl
import com.kailin.basic_arch.data.login.LoginRepository
import com.kailin.basic_arch.data.login.LoginRepositoryImpl
import com.kailin.basic_arch.data.news.NewsRepository
import com.kailin.basic_arch.data.news.NewsRepositoryImpl
import com.kailin.basic_arch.data.news.TaipeiNewsDataSource
import com.kailin.basic_arch.data.news.TaipeiNewsDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun provideLoginDataSource(dataSource: LoginDataSourceImpl): LoginDataSource

    @Binds
    abstract fun provideLoginRepository(repository: LoginRepositoryImpl): LoginRepository

    @Binds
    abstract fun provideTaipeiNewsDataSource(dataSource: TaipeiNewsDataSourceImpl): TaipeiNewsDataSource

    @Binds
    abstract fun provideTaipeiNewsRepository(repository: NewsRepositoryImpl): NewsRepository
}