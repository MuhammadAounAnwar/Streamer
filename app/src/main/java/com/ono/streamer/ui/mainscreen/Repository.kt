package com.ono.streamer.ui.mainscreen

import android.util.Log
import com.google.gson.Gson
import com.ono.streamerlibrary.RetrofitHelper
import com.ono.streamerlibrary.WebServices
import com.ono.streamerlibrary.models.ResponseModel
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

    private lateinit var response: ResponseModel

    override suspend fun getDefaultData(): ResponseModel = getData("action", 1)
    override suspend fun getSearchedData(query: String) = getData(query, 1)
    override suspend fun getNextPage(query: String, pageNo: Int): ResponseModel = getData(query, pageNo)

    private suspend fun getData(query: String, pageNo: Int): ResponseModel {
        kotlin.runCatching {
            response = RetrofitHelper.getInstance().create(WebServices::class.java).getMultiSearch("3d0cda4466f269e793e9283f6ce0b75e", "en-US", query, pageNo, false, "")
        }.onFailure {
            it.printStackTrace()
        }
        return response
    }
}