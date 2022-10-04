package com.ono.streamer.ui.mainscreen

import com.ono.streamerlibrary.ActivityScope
import dagger.Module
import dagger.Provides

@Module
class MainActivityRepoProvider {

    @ActivityScope
    @Provides
    fun repo(source: ISource): Repository = Repository.get(source)

    @ActivityScope
    @Provides
    fun createSource(): ISource = ISource.get()

}