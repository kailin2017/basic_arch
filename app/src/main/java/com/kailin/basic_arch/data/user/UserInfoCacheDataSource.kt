package com.kailin.basic_arch.data.user

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.kailin.basic_arch.BuildConfig
import com.kailin.basic_arch.model.user.UserInfo

object UserInfoCacheDataSource : UserInfoDataSource {

    private val userInfo = MutableLiveData<UserInfo>()

    override fun userInfo(): LiveData<UserInfo> = userInfo

    override fun setUserInfo(userInfo: UserInfo) = this.userInfo.postValue(userInfo)

    override fun clearUserInfo() = setUserInfo(UserInfo())

    override fun getUserHeader(): HashMap<String, String> {
        val headerMap = HashMap<String, String>()
        headerMap["X-Parse-Application-Id"] = BuildConfig.WATCH_MASTER_KEY
        userInfo.value?.let {
            headerMap["X-Parse-Session-Token"] = it.sessionToken
        }
        return headerMap
    }
}