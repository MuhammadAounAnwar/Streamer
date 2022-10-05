package com.ono.streamer.ui.mainscreen

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.ono.streamerlibrary.RetrofitHelper
import com.ono.streamerlibrary.WebServices
import com.ono.streamerlibrary.models.ErrorUtils
import com.ono.streamerlibrary.models.ResponseModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


private const val TAG = "IRepository"

interface Repository {
    companion object {
        fun get(source: ISource) = RepositoryImpl(source)
    }

    suspend fun getDefaultData(): ResponseModel
    suspend fun getSearchedData(query: String): ResponseModel
    suspend fun getNextPage(query: String, pageNo: Int): ResponseModel
}

interface ISource {
    companion object {
        fun get() = ISourceImpl()
    }

    suspend fun getDefaultData(): ResponseModel
    suspend fun getSearchedData(query: String): ResponseModel
    suspend fun getNextPage(query: String, pageNo: Int): ResponseModel
}

class RepositoryImpl(private val source: ISource) : Repository {
    override suspend fun getDefaultData(): ResponseModel = source.getDefaultData()
    override suspend fun getSearchedData(query: String): ResponseModel = source.getSearchedData(query)
    override suspend fun getNextPage(query: String, pageNo: Int): ResponseModel = source.getNextPage(query, pageNo)
}

class ISourceImpl : ISource {

    private var _resModel = MutableLiveData<ResponseModel>()
    val resModel: LiveData<ResponseModel> = _resModel
    private lateinit var responseModel: ResponseModel

    override suspend fun getDefaultData(): ResponseModel {
        val data = getData("action", 1)
        _resModel.postValue(data)
        return resModel.value!!
    }

    override suspend fun getSearchedData(query: String): ResponseModel {
        _resModel.value = getData(query, 1)
        return resModel.value!!
    }

    override suspend fun getNextPage(query: String, pageNo: Int): ResponseModel {
        _resModel.value = getData(query, pageNo)
        return resModel.value!!
    }

    private fun getData(query: String, pageNo: Int): ResponseModel {
        kotlin.runCatching {
            val res = RetrofitHelper.getInstance().create(WebServices::class.java).getMultiSearch("3d0cda4466f269e793e9283f6ce0b75e", "en-US", query, pageNo, false, "")
            res.enqueue(object : Callback<ResponseModel> {
                override fun onResponse(call: Call<ResponseModel>, response: Response<ResponseModel>) {
                    if (response.isSuccessful) {
                        responseModel = response.body() as ResponseModel
                        _resModel.postValue(responseModel)
                    } else {
                        val err = ErrorUtils.parseError(response)
                    }
                }

                override fun onFailure(call: Call<ResponseModel>, t: Throwable) {
                    Log.e(TAG, "onFailure: ", t)
                }
            })


        }.onFailure {
            it.printStackTrace()
        }
        return responseModel
    }
}