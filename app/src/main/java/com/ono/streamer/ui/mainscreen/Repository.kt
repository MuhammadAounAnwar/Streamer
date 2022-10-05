package com.ono.streamer.ui.mainscreen

import android.util.Log
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.google.gson.stream.JsonReader
import com.ono.streamerlibrary.RetrofitHelper
import com.ono.streamerlibrary.WebServices
import com.ono.streamerlibrary.models.ResponseModel
import retrofit2.Response
import java.io.StringReader
import java.lang.reflect.Type


private const val TAG = "IRepository"

interface Repository {
    companion object {
        fun get(source: ISource) = RepositoryImpl(source)
    }

    suspend fun getAllData(): ResponseModel
}

interface ISource {
    companion object {
        fun get() = ISourceImpl()
    }

    suspend fun getAllData(): ResponseModel
}

class RepositoryImpl(private val source: ISource) : Repository {
    override suspend fun getAllData(): ResponseModel = source.getAllData()

}


class ISourceImpl : ISource {

    private lateinit var response: ResponseModel

    override suspend fun getAllData(): ResponseModel {
        kotlin.runCatching {
            response = RetrofitHelper.getInstance().create(WebServices::class.java).getMultiSearch("3d0cda4466f269e793e9283f6ce0b75e", "en-US", "action", 1, false, "")


//            response = when (apiResponse.code()) {
//                200 -> com.ono.streamerlibrary.models.JsonParser.parseResponse(apiResponse)
//                401, 404 -> convertJsonResponse(apiResponse, ErrorResponse::class.java)
//                else -> {}
//            }

        }.onFailure {
            it.printStackTrace()
        }
        return response
    }

    private fun convertSuccessResponse(response: Response<Any>, parseClass: Class<*>) {
        val gson = Gson()
        val msg = response.body().toString()
//        Log.e(TAG, "convertSuccessResponse--1: $msg")
        val reader = JsonReader(StringReader(msg))
        reader.isLenient = true
        kotlin.runCatching {

            val collectionType: Type = object : TypeToken<ResponseModel?>() {}.type
            val articles: ResponseModel = gson.fromJson(reader, collectionType)
            Log.e(TAG, "convertSuccessResponse--2: ${gson.toJson(articles)}")

//            val testModel = gson.fromJson(reader, parseClass) as ResponseModel

//            val moshi = Moshi.Builder().build()
//            val jsonAdapter: JsonAdapter<ResponseModel> = moshi.adapter(ResponseModel::class.java)
//            val responseModel: ResponseModel = jsonAdapter.fromJson(msg)!!

//            Log.e(TAG, "convertSuccessResponse--2: $responseModel")
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