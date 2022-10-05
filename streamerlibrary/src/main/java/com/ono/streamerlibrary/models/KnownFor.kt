package com.ono.streamerlibrary.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class KnownFor(
    @SerializedName("poster_path") @Expose var poster_path: String? = null,

    @SerializedName("adult") @Expose var adult: Boolean? = null,

    @SerializedName("overview") @Expose var overview: String? = null,

    @SerializedName("release_date") @Expose var release_date: String? = null,

    @SerializedName("original_title") @Expose var original_title: String? = null,

    @SerializedName("genre_ids") @Expose var genre_ids: List<Int>? = null,

    @SerializedName("id") @Expose var id: Int? = null,

    @SerializedName("media_type") @Expose var media_type: String? = null,

    @SerializedName("original_language") @Expose var original_language: String? = null,

    @SerializedName("title") @Expose var title: String? = null,

    @SerializedName("backdrop_path") @Expose var backdrop_path: String = "backdrop_path",

    @SerializedName("popularity") @Expose var popularity: Double? = null,

    @SerializedName("vote_count") @Expose var vote_count: Int? = null,

    @SerializedName("video") @Expose var video: Boolean? = null,

    @SerializedName("vote_average") @Expose var vote_average: Double? = null,

    @SerializedName("first_air_date") @Expose var first_air_date: String? = null,

    @SerializedName("origin_country") @Expose var origin_country: List<String>? = null,

    @SerializedName("name") @Expose var name: String? = null,

    @SerializedName("original_name") @Expose var original_name: String? = null
)
