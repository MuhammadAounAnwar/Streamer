package com.ono.streamerlibrary.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class ProfileModel(
    @SerializedName("adult") @Expose var adult: Boolean? = null,
    @SerializedName("gender") @Expose var gender: Int? = null,
    @SerializedName("known_for_department") @Expose var known_for_department: String? = null,
    @SerializedName("known_for") @Expose var known_for: List<KnownFor>? = arrayListOf(),
    @SerializedName("profile_path") @Expose var profile_path: String? = null,
    @SerializedName("media_type") @Expose var media_type: String? = null,
    @SerializedName("popularity") @Expose var popularity: Double? = null,
    @SerializedName("id") @Expose var id: Int? = null,
    @SerializedName("name") @Expose var name: String? = null

)
