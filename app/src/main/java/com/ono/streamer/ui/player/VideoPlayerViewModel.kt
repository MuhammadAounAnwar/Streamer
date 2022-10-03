package com.ono.streamer.ui.player

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel

class VideoPlayerViewModel(val savedStateHandle: SavedStateHandle, val applicationContext: Application) : ViewModel() {

    private var _videoUrl: MutableLiveData<String> = MutableLiveData()
    val videoUrl: LiveData<String> = _videoUrl

    fun initViewModel(url: String) {
        _videoUrl.value = url
    }
}