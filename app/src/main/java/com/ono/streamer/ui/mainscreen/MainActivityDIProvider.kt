package com.ono.streamer.ui.mainscreen

import dagger.Module

@Module(includes = [MainActivityViewsProvider::class, MainActivityRepoProvider::class])
class MainActivityDIProvider {
}