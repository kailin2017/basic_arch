package com.kailin.basic_arch.utils.connect

import android.content.Context
import com.bumptech.glide.Glide
import com.bumptech.glide.Registry
import com.bumptech.glide.annotation.GlideModule
import com.bumptech.glide.integration.okhttp3.OkHttpUrlLoader
import com.bumptech.glide.load.model.GlideUrl
import com.bumptech.glide.module.AppGlideModule
import com.kailin.basic_arch.di.AppModule
import okhttp3.OkHttpClient
import java.io.InputStream

@GlideModule
class GlideModule : AppGlideModule() {

    @AppModule.DefaultOkHttpClient
    private lateinit var okHttpClient: OkHttpClient

    override fun registerComponents(context: Context, glide: Glide, registry: Registry) {
        super.registerComponents(context, glide, registry)
        val urlLoader = OkHttpUrlLoader.Factory(okHttpClient)
        registry.append(GlideUrl::class.java, InputStream::class.java, urlLoader)
    }
}