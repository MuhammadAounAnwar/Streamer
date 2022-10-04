package com.ono.streamerlibrary

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface WebServices {
    @GET("registerUser")
    suspend fun getRegisteredUsers(@Query("username") userName: String, @Query("externalId") externalId: String): Response<Any>

}