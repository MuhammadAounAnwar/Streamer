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

    private var defaultMovies = ArrayList<Result>()
    var defaultTvShows = ArrayList<Result>()
    var defaultProfiles = ArrayList<Result>()

    var _loader = MutableLiveData<Boolean>(false)

    private var _moviesList = MutableLiveData<List<Result>>(ArrayList())
    val moviesList: LiveData<List<Result>> = _moviesList

    private var _tvShowsList = MutableLiveData<ArrayList<Result>>(ArrayList())
    val tvShowsList: LiveData<ArrayList<Result>> = _tvShowsList

    private var _profilesList = MutableLiveData<ArrayList<Result>>(ArrayList())
    val profilesList: LiveData<ArrayList<Result>> = _profilesList

    private var _selectedItem = MutableLiveData<Result>()
    val selectedItem: LiveData<Result> = _selectedItem

    fun initViewModel() {
        getDefaultData()
    }

    private fun getDefaultData() {
        _loader.value = true
        scope.launch(Dispatchers.IO) {
            val response = repository.getDefaultData()
            filterResponseData(response)
        }
    }

    private fun filterResponseData(response: ResponseModel) {
        if (response.results != null && response.results!!.isNotEmpty()) {
            movies.clear()
            tvShows.clear()
            profiles.clear()

            for (item in response.results!!) {
                when (item.media_type) {
                    "movie" -> movies.add(item)
                    "tv" -> tvShows.add(item)
                    "person" -> profiles.add(item)
                }
            }
//            setDefaultData()
            sendDataToUI()
        }
    }

    private fun setDefaultData() {
        if (defaultMovies.size == 0) {
            defaultMovies.addAll(movies)
        }
        if (defaultTvShows.size == 0) {
            defaultProfiles.addAll(tvShows)
        }
        if (defaultProfiles.size == 0) {
            defaultProfiles.addAll(profiles)
        }
    }

    private fun sendDataToUI() {
        scope.launch(Dispatchers.Main) {
            _loader.value = false
            _moviesList.value = movies
            _tvShowsList.value = tvShows
            _profilesList.value = profiles

            _moviesList.postValue(movies)
            _tvShowsList.postValue(tvShows)
            _profilesList.postValue(profiles)
        }
    }

    fun getSearchedResult(query: String) {
        if (query.isNotEmpty()) {
            _loader.value = true
            scope.launch(Dispatchers.IO) {
                val response = repository.getSearchedData(query)
                filterResponseData(response)
            }
        }

    }

    private fun showErrorMessage(response: Any) {
        errorResponse = response as ErrorResponse
    }

    fun defineSelectedItem(result: Result) {
        _selectedItem.value = result
    }


}