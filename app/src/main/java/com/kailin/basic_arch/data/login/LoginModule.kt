package com.kailin.basic_arch.data.login

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
abstract class LoginModule {

    @Binds
    abstract fun provideDataSource(dataSource: LoginDataSourceImpl): LoginDataSource

    @Binds
    abstract fun provideRepository(repository: LoginRepositoryImpl): LoginRepository
}