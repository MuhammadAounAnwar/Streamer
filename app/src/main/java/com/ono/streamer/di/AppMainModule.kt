package com.ono.streamer.di

import android.app.Application
import android.content.Context
import com.ono.streamer.StreamerClass
import dagger.Binds
import dagger.Module

@Module(includes = [AppActivitiesModule::class, AppNetworkModule::class])
abstract class AppMainModule {

    @Binds
    abstract fun bindsApp(streamerClass: StreamerClass): Application

    @Binds
    abstract fun bindsContext(application: Application): Context


}