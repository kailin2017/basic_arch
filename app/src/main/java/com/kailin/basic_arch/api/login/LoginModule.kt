package com.kailin.basic_arch.api.login

import com.kailin.basic_arch.utils.connect.ConnectHelper
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
object LoginModule {

    @Provides
    fun provideService() = ConnectHelper.createService(LoginService::class.java)
}