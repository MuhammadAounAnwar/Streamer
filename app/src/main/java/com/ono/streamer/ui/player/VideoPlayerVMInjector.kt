package com.ono.streamer.ui.player

import android.app.Application
import androidx.lifecycle.SavedStateHandle
import com.ono.streamer.ui.ViewModelInjector
import com.ono.streamerlibrary.ActivityScope
import javax.inject.Inject

@ActivityScope
class VideoPlayerVMInjector @Inject constructor(private val applicationContext: Application) : ViewModelInjector<VideoPlayerViewModel> {
    override fun create(savedStateHandle: SavedStateHandle): VideoPlayerViewModel = VideoPlayerViewModel(savedStateHandle, applicationContext)
}