package com.ono.streamer.ui.mainscreen

import com.ono.streamerlibrary.FragmentScope
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class MainActivityViewsProvider {
    @ContributesAndroidInjector
    @FragmentScope
    abstract fun createMainFragment(): MainFragment

    @ContributesAndroidInjector
    @FragmentScope
    abstract fun createDetailFragment(): DetailFragment
}