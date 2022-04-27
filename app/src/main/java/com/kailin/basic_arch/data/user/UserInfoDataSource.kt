package com.kailin.basic_arch.data.user

import androidx.lifecycle.LiveData
import com.kailin.basic_arch.model.user.UserInfo

interface UserInfoDataSource {

    fun userInfo(): LiveData<UserInfo>

    fun setUserInfo(userInfo: UserInfo)

    fun clearUserInfo()

    fun getUserHeader(): HashMap<String, String>
}