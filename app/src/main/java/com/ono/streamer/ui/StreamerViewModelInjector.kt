package com.ono.streamer.ui

import android.app.Application
import androidx.lifecycle.SavedStateHandle
import com.ono.streamerlibrary.ActivityScope
import javax.inject.Inject

@ActivityScope
class StreamerViewModelInjector @Inject constructor(private val applicationContext: Application) : ViewModelInjector<StreamerViewModel> {
    override fun create(savedStateHandle: SavedStateHandle): StreamerViewModel = StreamerViewModel(savedStateHandle, applicationContext)
}