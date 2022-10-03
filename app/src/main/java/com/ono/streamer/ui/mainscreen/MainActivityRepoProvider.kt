package com.ono.streamer.ui.mainscreen

import com.ono.streamerlibrary.ActivityScope
import dagger.Module
import dagger.Provides

@Module
class MainActivityRepoProvider {

    @Provides
    @ActivityScope
    fun repo(source: ISource) = IRepository.get(source)

    @Provides
    @ActivityScope
    fun source() = ISource.get()
}