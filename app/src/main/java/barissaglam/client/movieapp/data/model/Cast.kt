package barissaglam.client.movieapp.data.model

import com.google.gson.annotations.SerializedName

data class Cast(
    @SerializedName("cast_id") val castId: Int?,
    @SerializedName("character") val character: String?,
    @SerializedName("gender") val gender: Int?,
    @SerializedName("credit_id") val creditId: String?,
    @SerializedName("name") val name: String?,
    @SerializedName("profile_path") val profilePath: String?,
    @SerializedName("id") val id: Int?,
    @SerializedName("order") val order: Int?
)