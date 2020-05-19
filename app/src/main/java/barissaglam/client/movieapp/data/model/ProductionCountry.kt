package barissaglam.client.movieapp.data.model

import com.google.gson.annotations.SerializedName

data class ProductionCountry(
    @SerializedName("iso_3166_1") val iso: String?,
    @SerializedName("name") val name: String?
)