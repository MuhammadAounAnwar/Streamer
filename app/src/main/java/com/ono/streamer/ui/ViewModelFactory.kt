package com.ono.streamer.ui

import android.os.Bundle
import androidx.lifecycle.AbstractSavedStateViewModelFactory
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.savedstate.SavedStateRegistryOwner

interface ViewModelInjector<T : ViewModel> {
    fun create(savedStateHandle: SavedStateHandle): T
}

class ViewModelFactory<T : ViewModel>(private val injector: ViewModelInjector<T>, savedStateRegistryOwner: SavedStateRegistryOwner, bundle: Bundle?) :
    AbstractSavedStateViewModelFactory(savedStateRegistryOwner, bundle) {
    override fun <T : ViewModel?> create(key: String, modelClass: Class<T>, handle: SavedStateHandle): T = injector.create(handle) as T
}