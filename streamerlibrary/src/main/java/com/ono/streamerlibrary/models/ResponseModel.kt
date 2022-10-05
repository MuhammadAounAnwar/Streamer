package com.ono.streamerlibrary.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class ResponseModel(
    @SerializedName("page") @Expose var page: Int = 0,

    @SerializedName("results") @Expose var results: List<Result>? = arrayListOf(),

    @SerializedName("total_results") @Expose var totalResults: Int = 0,

    @SerializedName("total_pages") @Expose var totalPages: Int = 0,

    @SerializedName("status_message") @Expose var statusMessage: String? = null,

    @SerializedName("success") @Expose var success: Boolean? = null,

    @SerializedName("status_code") @Expose var statusCode: Int = 0
)