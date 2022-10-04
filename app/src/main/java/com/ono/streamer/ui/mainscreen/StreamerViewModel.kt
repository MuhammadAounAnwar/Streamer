package com.ono.streamer.ui.mainscreen

import android.app.Application
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.*

private const val TAG = "StreamerViewModel"

class StreamerViewModel(val savedStateHandle: SavedStateHandle, val applicationContext: Application, private val repository: Repository) : ViewModel() {

    private val handler = CoroutineExceptionHandler { _, _ ->
    }
    private val scope = CoroutineScope(viewModelScope.coroutineContext + handler)
    val jobScope = CoroutineScope(Job() + Dispatchers.Main)

    fun initViewModel() {
        getMoviesResponse()
    }

    private fun getMoviesResponse() {
        scope.launch(Dispatchers.IO) {
            val response = repository.getAllData()
        }
    }


}