package com.kailin.basic_arch.data.news

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
abstract class TaipeiNewsModule {

    @Binds
    abstract fun provideDataSource(dataSource: TaipeiNewsDataSourceImpl): TaipeiNewsDataSource

    @Binds
    abstract fun provideRepository(repository: NewsRepositoryImpl): NewsRepository
}