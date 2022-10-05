package com.ono.streamerlibrary.models

import android.util.Log
import com.google.gson.Gson
import org.json.JSONArray
import org.json.JSONObject
import retrofit2.Response

private const val TAG = "JsonParser"

object JsonParser {

    val gson = Gson()

    fun parseResponse(response: Response<*>) {
        kotlin.runCatching {
            val res = JSONObject(response.body().toString())
            if (res.has("results")) {
                val results = JSONArray(res.getJSONArray("results"))
                if (results.length() > 0) {
                    for (index in 0 until results.length()) {
//                        Log.e(TAG, "parseResponse: ${results.getJSONObject(index).toString()} \n")
//                    val result = JSONObject(results.getJSONObject(index).toString())

//                    if (result.has("media_type")) {
//                        when (result.get("media_type")) {
//                            "movie", "tv" -> convertJsonResponse(result, Result::class.java)
//                            "person" -> convertJsonResponse(result, ProfileModel::class.java)
//                        }
//                    }
                    }
                }
            }
        }.onFailure {
            it.printStackTrace()
        }
    }

    private fun convertJsonResponse(jsonObject: JSONObject, parseClass: Class<*>) {
        val testModel = gson.fromJson(jsonObject.toString(), parseClass)
        Log.e(TAG, "convertJsonResponse: $testModel")
    }

}