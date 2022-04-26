package com.kailin.basic_arch.api.news

import com.kailin.basic_arch.utils.connect.ConnectHelper
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import javax.inject.Singleton

@Module
@InstallIn(ViewModelComponent::class)
object TaipeiNewsModule {

    @Provides
    fun provideService() = ConnectHelper.createService(TaipeiNewsService::class.java)
}