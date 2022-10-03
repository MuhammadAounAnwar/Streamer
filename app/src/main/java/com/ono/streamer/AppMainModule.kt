package com.ono.streamer

import android.app.Application
import android.content.Context
import dagger.Binds
import dagger.Module

@Module(includes = [AppActivitiesModule::class, AppNetworkModule::class])
abstract class AppMainModule {

    @Binds
    abstract fun bindsApp(streamerClass: StreamerClass): Application

    @Binds
    abstract fun bindsContext(application: Application): Context


}