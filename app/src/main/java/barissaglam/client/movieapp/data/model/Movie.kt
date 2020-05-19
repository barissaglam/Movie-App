package barissaglam.client.movieapp.data.model

import com.google.gson.annotations.SerializedName

data class Movie(
    @SerializedName("popularity") val popularity: Double?,
    @SerializedName("vote_count") val vote_count: Double?,
    @SerializedName("video") val video: Boolean?,
    @SerializedName("poster_path") val posterPath: String?,
    @SerializedName("id") val id: Int?,
    @SerializedName("adult") val adult: Boolean?,
    @SerializedName("backdrop_path") val backdropPath: String?,
    @SerializedName("original_language") val originalLanguage: String?,
    @SerializedName("original_title") val originalTitle: String?,
    @SerializedName("genre_ids") val genreIds: ArrayList<Int>?,
    @SerializedName("title") val title: String?,
    @SerializedName("vote_average") val voteAverage: Double?,
    @SerializedName("overview") val overview: String?,
    @SerializedName("release_date") val releaseDate: String?
)