package com.ono.streamer.ui.player

import com.ono.streamer.ui.mainscreen.MainActivityRepoProvider
import dagger.Module

@Module(includes = [MainActivityRepoProvider::class])
abstract class VideoPlayerDIProvider {}