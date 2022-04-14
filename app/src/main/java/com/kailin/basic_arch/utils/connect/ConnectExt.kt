package com.kailin.basic_arch.utils.connect

import com.kailin.basic_arch.utils.GsonHelper
import okhttp3.ResponseBody
import java.lang.reflect.Type

fun <T> ResponseBody.toData(type: Type): T {
    return GsonHelper.fromJson(string(), type)
}