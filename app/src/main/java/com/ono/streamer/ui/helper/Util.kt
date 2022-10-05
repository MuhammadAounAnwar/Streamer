package com.ono.streamer.ui.helper

import android.util.Log
import com.google.gson.Gson
import retrofit2.Response

object Util {

    private val TAG = "Util"

    private fun convertJsonResponse(response: Response<Any>, parseClass: Class<*>) {
        val gson = Gson()
        val msg = response.errorBody()?.string()
        val testModel = gson.fromJson(msg, parseClass)
        Log.e(TAG, "convertJsonResponse: $testModel")
    }
}