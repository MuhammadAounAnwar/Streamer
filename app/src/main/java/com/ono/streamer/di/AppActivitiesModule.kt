package com.ono.streamer.di

import com.ono.streamer.ui.mainscreen.MainActivity
import com.ono.streamer.ui.mainscreen.MainActivityDIProvider
import com.ono.streamer.ui.player.VideoPlayerActivity
import com.ono.streamerlibrary.ActivityScope
import dagger.Module
import dagger.android.ContributesAndroidInjector


@Module
abstract class AppActivitiesModule {

    @ActivityScope
    @ContributesAndroidInjector(modules = [MainActivityDIProvider::class])
    abstract fun createMainActivity(): MainActivity


//    @ActivityScope
//    @ContributesAndroidInjector()
//    abstract fun createPlayerActivity(): VideoPlayerActivity
}