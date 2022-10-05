package com.ono.streamer.ui.mainscreen

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import com.ono.streamer.MediaAdapter
import com.ono.streamer.ui.helper.MediaType
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
    private var tvShows = ArrayList<Result>()
    private var profiles = ArrayList<Result>()

    var _loader = MutableLiveData(false)
    var isLastPage = false
    var isLoading = false
    var pageCount = 1

    private var _query = MutableLiveData("action")
    val query: LiveData<String> = _query

    private var _currentPage = MutableLiveData(1)
    val currentPage: LiveData<Int> = _currentPage

    private var _dtoResponseModel = MutableLiveData<ResponseModel>()
    private val dtoResponseModel: LiveData<ResponseModel> = _dtoResponseModel

    private var _moviesList = MutableLiveData<List<Result>>(ArrayList())
    val moviesList: LiveData<List<Result>> = _moviesList

    private var _tvShowsList = MutableLiveData<ArrayList<Result>>(ArrayList())
    val tvShowsList: LiveData<ArrayList<Result>> = _tvShowsList

    private var _profilesList = MutableLiveData<ArrayList<Result>>(ArrayList())
    val profilesList: LiveData<ArrayList<Result>> = _profilesList

    private var _selectedItem = MutableLiveData<Result>()
    val selectedItem: LiveData<Result> = _selectedItem

    private val _nextPageData = MutableLiveData<ResponseModel>()
    val nextPageData: LiveData<ResponseModel> = _nextPageData

    fun initViewModel() {
        getDefaultData()
    }

    private fun resetData() {
        movies.clear()
        tvShows.clear()
        profiles.clear()
    }

    private fun getDefaultData() {
        scope.launch(Dispatchers.IO) {
            val response = repository.getDefaultData()
            _dtoResponseModel.postValue(response)
            resetData()
            filterResponseData(response)
            populateDataToUI()
        }
    }

    fun getSearchedResult(query: String) {
        if (query.isNotEmpty()) {
            _query.value = query
            scope.launch(Dispatchers.IO) {
                val response = repository.getSearchedData(query)
                _dtoResponseModel.postValue(response)
                resetData()
                filterResponseData(response)
                populateDataToUI()
            }
        }
    }

    private fun getNextPage(pageNo: Int) {
        scope.launch(Dispatchers.IO) {
            val response = repository.getNextPage(query.value!!, pageNo)
            _dtoResponseModel.postValue(response)
            resetData()
            filterResponseData(response)
            isLoading = false
            _loader.postValue(false)
        }
    }

    private fun filterResponseData(response: ResponseModel) {
        if (response.results != null && response.results!!.isNotEmpty()) {
            for (item in response.results!!) {
                when (item.media_type) {
                    "movie" -> movies.add(item)
                    "tv" -> tvShows.add(item)
                    "person" -> profiles.add(item)
                }
            }

            tvShows.forEach { tvshow ->
                Log.e(TAG, "filterResponseData: $tvshow")
            }

        }
    }

    private fun populateDataToUI() {
        scope.launch(Dispatchers.Main) {
            _moviesList.value = movies
            _tvShowsList.value = tvShows
            _profilesList.value = profiles
        }
    }

    fun loadNextPage() {
        if (!isLoading) {
            isLoading = true
            pageCount += 1
            if (pageCount < dtoResponseModel.value?.totalPages!!) {
                _loader.value = true
                getNextPage(pageCount)
            } else {
                isLastPage = true
            }
        }
    }

    fun addNewValuesToRV(adapter: MediaAdapter, mediaType: MediaType) {
        when (mediaType) {
            MediaType.MOVIE -> adapter.addNewItems(movies)
            MediaType.TV -> adapter.addNewItems(tvShows)
            MediaType.PERSON -> adapter.addNewItems(profiles)
        }
    }

    fun defineSelectedItem(result: Result) {
        _selectedItem.value = result
    }

    private fun showErrorMessage(response: Any) {
        errorResponse = response as ErrorResponse
    }


}