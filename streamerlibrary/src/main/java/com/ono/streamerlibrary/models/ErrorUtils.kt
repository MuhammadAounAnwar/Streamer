package com.ono.streamerlibrary.models

import org.json.JSONObject
import retrofit2.Response

object ErrorUtils {
    fun parseError(response: Response<*>): APIError {
        var bodyObj: JSONObject? = null
        var success: Boolean = false
        var message: String = "Error Message"
        var code: Int = 0
        try {
            val errorBody: String = response.errorBody()!!.string()
            bodyObj = JSONObject(errorBody)
            if (bodyObj.has("success")) {
                success = bodyObj.getBoolean("success")
            }
            if (bodyObj.has("status_message")) {
                message = bodyObj.getString("status_message")
            }
            if (bodyObj.has("status_code")) {
                code = bodyObj.getInt("status_code")
            }
        } catch (e: Exception) {
            e.printStackTrace()
            success = false
            message = e.toString()
        }
        return APIError.Builder()
            .success(success)
            .status_message(message)
            .status_code(code)
            .build()
    }
}
