package barissaglam.client.movieapp.data.model

import com.google.gson.annotations.SerializedName

data class Genre(
    @SerializedName("name") val name: String?,
    @SerializedName("id") val id: Int?
)