package barissaglam.client.movieapp.data.model.response

import barissaglam.client.movieapp.data.model.Cast
import com.google.gson.annotations.SerializedName

data class CastResponse(
    @SerializedName("cast") val cast: List<Cast>?,
    @SerializedName("id") val id: Int?
)