package com.ono.streamer.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Result(
    @SerializedName("poster_path") @Expose var posterPath: Any? = null,

    @SerializedName("popularity") @Expose var popularity: Double? = null,

    @SerializedName("id") @Expose var id: Int? = null,

    @SerializedName("overview") @Expose var overview: String? = null,

    @SerializedName("backdrop_path") @Expose var backdropPath: Any? = null,

    @SerializedName("vote_average") @Expose var voteAverage: Int? = null,

    @SerializedName("media_type") @Expose var mediaType: String? = null,

    @SerializedName("first_air_date") @Expose var firstAirDate: String? = null,

    @SerializedName("origin_country") @Expose var originCountry: List<String>? = null,

    @SerializedName("genre_ids") @Expose var genreIds: List<Int>? = null,

    @SerializedName("original_language") @Expose var originalLanguage: String? = null,

    @SerializedName("vote_count") @Expose var voteCount: Int? = null,

    @SerializedName("name") @Expose var name: String? = null,

    @SerializedName("original_name") @Expose var originalName: String? = null,

    @SerializedName("adult") @Expose var adult: Boolean? = null,

    @SerializedName("release_date") @Expose var releaseDate: String? = null,

    @SerializedName("original_title") @Expose var originalTitle: String? = null,

    @SerializedName("title") @Expose var title: String? = null,

    @SerializedName("video") @Expose var video: Boolean? = null,

    @SerializedName("profile_path") @Expose var profilePath: String? = null,

    @SerializedName("known_for") @Expose var knownFor: List<KnownFor>? = null
)
