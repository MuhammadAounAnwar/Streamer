package com.ono.streamerlibrary.models

import com.google.gson.annotations.Expose

import com.google.gson.annotations.SerializedName


data class ErrorResponse(
    @SerializedName("status_message") @Expose var statusMessage: String? = null,

    @SerializedName("success") @Expose var success: Boolean? = null,

    @SerializedName("status_code") @Expose var statusCode: Int? = null
)