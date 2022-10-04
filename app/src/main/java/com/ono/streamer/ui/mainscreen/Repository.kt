package com.ono.streamer.ui.mainscreen

import android.util.Log
import com.google.gson.Gson
import com.ono.streamerlibrary.RetrofitHelper
import com.ono.streamerlibrary.WebServices
import com.ono.streamerlibrary.models.ErrorResponse
import com.ono.streamerlibrary.models.ResponseModel
import retrofit2.Response


private const val TAG = "IRepository"

interface Repository {
    companion object {
        fun get(source: ISource) = RepositoryImpl(source)
    }

    suspend fun getAllData(): Any
}

interface ISource {
    companion object {
        fun get() = ISourceImpl()
    }

    suspend fun getAllData(): Any
}

class RepositoryImpl(private val source: ISource) : Repository {
    override suspend fun getAllData(): Any = source.getAllData()

}


class ISourceImpl : ISource {

    private lateinit var response: Any

    override suspend fun getAllData(): Any {
        kotlin.runCatching {
            val apiResponse = RetrofitHelper.getInstance().create(WebServices::class.java).getMultiSearch("3d0cda4466f269e793e9283f6ce0b75e", "en-US", "action", 1, false, "")

            response = when (apiResponse.code()) {
                200 -> convertSuccessResponse(apiResponse, ResponseModel::class.java)
                401, 404 -> convertJsonResponse(apiResponse, ErrorResponse::class.java)
                else -> {}
            }

        }.onFailure {
            it.printStackTrace()
        }
        return response
    }

    private fun convertSuccessResponse(response: Response<Any>, parseClass: Class<*>) {
        val gson = Gson()
        val msg = response.body().toString()
        Log.e(TAG, "convertSuccessResponse--1: $msg")
        kotlin.runCatching {
            val testModel = gson.fromJson(msg, parseClass)
            Log.e(TAG, "convertSuccessResponse--2: $testModel")
        }.onFailure {
            it.printStackTrace()
        }
    }

    private fun convertJsonResponse(response: Response<Any>, parseClass: Class<*>) {
        val gson = Gson()
        val msg = response.errorBody()?.string()
        val testModel = gson.fromJson(msg, parseClass)
        Log.e(TAG, "convertJsonResponse: $testModel")
    }


}