package com.ono.streamer.di

import android.content.Context
import android.util.Log
import com.bumptech.glide.GlideBuilder
import com.bumptech.glide.annotation.GlideModule
import com.bumptech.glide.load.engine.executor.GlideExecutor
import com.bumptech.glide.module.AppGlideModule
import com.ono.streamer.BuildConfig

@GlideModule
class BaseAppGlideModule : AppGlideModule() {
    override fun applyOptions(context: Context, builder: GlideBuilder) {
        if (BuildConfig.DEBUG) {
            builder.setLogLevel(Log.ERROR)
        }
        builder.setAnimationExecutor(GlideExecutor.newUnlimitedSourceExecutor())

        //super.applyOptions(context, builder);
    }
}