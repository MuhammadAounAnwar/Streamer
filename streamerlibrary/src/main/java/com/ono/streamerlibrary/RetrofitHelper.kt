package com.ono.streamerlibrary

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object RetrofitHelper {
    const val baseUrl = "https://api.themoviedb.org/"
    const val baseUrlApi = "${baseUrl}3/"

    fun getInstance(): Retrofit {
        return Retrofit.Builder().baseUrl(baseUrlApi)
            .addConverterFactory(GsonConverterFactory.create())
            .client(getClient())
            .build()
    }

    private fun getClient(): OkHttpClient {

        val builder = OkHttpClient.Builder()
        val logging = HttpLoggingInterceptor()

        logging.level = HttpLoggingInterceptor.Level.BODY
        builder.readTimeout(2, TimeUnit.MINUTES)
        builder.connectTimeout(30, TimeUnit.SECONDS)
        builder.writeTimeout(2, TimeUnit.MINUTES)


        if (BuildConfig.DEBUG) {
            builder.addInterceptor(logging)
        }
        return builder.build()
    }
}