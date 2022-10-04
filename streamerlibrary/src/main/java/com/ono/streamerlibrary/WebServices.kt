package com.ono.streamerlibrary

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface WebServices {
    @GET("search/multi?")
    suspend fun getMultiSearch(
        @Query("api_key") api_key: String,
        @Query("language") language: String,
        @Query("query") query: String,
        @Query("page") page: Int,
        @Query("include_adult") include_adult: Boolean,
        @Query("region") region: String
    ): Response<Any>

}