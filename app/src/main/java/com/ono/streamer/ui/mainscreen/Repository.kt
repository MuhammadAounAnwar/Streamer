package com.ono.streamer.ui.mainscreen

import android.util.Log
import com.ono.streamerlibrary.RetrofitHelper
import com.ono.streamerlibrary.WebServices
import com.ono.streamerlibrary.models.ResponseModel

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
    override suspend fun getAllData(): ResponseModel {
        val api = RetrofitHelper.getInstance().create(WebServices::class.java)
        val response = api.getMultiSearch("3d0cda4466f269e793e9283f6ce0b75e", "en-US", "action", 1, false, "")

        Log.e(TAG, "getAllData: $response")

//        if (response.code() == 200) {
//            val gson = Gson()
//            val getFAQs = gson.fromJson(response.body() as Response, Response::class.java)
//        } else if (response.code() == 401) {
//
//        } else if (response.code() == 404) {
//
//        }

        return response
    }

}