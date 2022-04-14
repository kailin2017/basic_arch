package com.kailin.basic_arch.app

import android.app.Application
import com.kailin.basic_arch.data.login.UserInfoDataSource

class MyApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        instance = this
        UserInfoDataSource.clearUserInfo()
    }

    companion object {
        lateinit var instance: MyApplication
    }
}