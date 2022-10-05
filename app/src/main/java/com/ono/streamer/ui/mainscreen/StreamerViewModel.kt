package com.ono.streamer.ui.mainscreen

import android.app.Application
import androidx.lifecycle.*
import com.ono.streamerlibrary.models.ErrorResponse
import com.ono.streamerlibrary.models.ResponseModel
import com.ono.streamerlibrary.models.Result
import kotlinx.coroutines.*

private const val TAG = "StreamerViewModel"

class StreamerViewModel(val savedStateHandle: SavedStateHandle, val applicationContext: Application, private val repository: Repository) : ViewModel() {

    private val handler = CoroutineExceptionHandler { _, _ ->
    }
    private val scope = CoroutineScope(viewModelScope.coroutineContext + handler)
    val jobScope = CoroutineScope(Job() + Dispatchers.Main)

    private lateinit var responseModel: ResponseModel
    private lateinit var errorResponse: ErrorResponse
    private var movies = ArrayList<Result>()
    var tvShows = ArrayList<Result>()
    var profiles = ArrayList<Result>()

    private var _moviesList = MutableLiveData<List<Result>>(ArrayList())
    val moviesList: LiveData<List<Result>> = _moviesList

    private var _tvShowsList = MutableLiveData<ArrayList<Result>>(ArrayList())
    val tvShowsList: LiveData<ArrayList<Result>> = _tvShowsList

    private var _profilesList = MutableLiveData<ArrayList<Result>>(ArrayList())
    val profilesList: LiveData<ArrayList<Result>> = _profilesList

    fun initViewModel() {
        getMoviesResponse()
    }

    private fun getMoviesResponse() {
        scope.launch(Dispatchers.IO) {

            val response = repository.getAllData()
            filterMoviesData(response)

//            when (val response = repository.getAllData()) {
//                is ResponseModel -> filterMoviesData(response)
//                is ErrorResponse -> showErrorMessage(response)
//            }
        }
    }

    private fun filterMoviesData(response: ResponseModel) {
        for (item in response.results!!) {
            when (item.media_type) {
                "movie" -> movies.add(item)
                "tv" -> tvShows.add(item)
                "person" -> profiles.add(item)
            }
        }

        scope.launch(Dispatchers.Main) {
            _moviesList.value = movies
            _tvShowsList.value = tvShows
            _profilesList.value = profiles

            _moviesList.postValue(movies)
            _tvShowsList.postValue(tvShows)
            _profilesList.postValue(profiles)
        }
    }

    private fun showErrorMessage(response: Any) {
        errorResponse = response as ErrorResponse
    }


}