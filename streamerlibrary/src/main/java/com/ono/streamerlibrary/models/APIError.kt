package com.ono.streamerlibrary.models

class APIError(
    val status_message: String?,
    val status_code: Int,
    val success: Boolean
) {

    data class Builder(
        var status_message: String? = null,
        var status_code: Int = 0,
        var success: Boolean = false
    ) {

        fun status_message(status_message: String) = apply { this.status_message = status_message }
        fun status_code(status_code: Int) = apply { this.status_code = status_code }
        fun success(success: Boolean) = apply { this.success = success }
        fun build() = APIError(status_message, status_code, success)
    }
}
