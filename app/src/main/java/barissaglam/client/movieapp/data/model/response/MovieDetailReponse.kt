package barissaglam.client.movieapp.data.model.response

import barissaglam.client.movieapp.data.model.Genre
import barissaglam.client.movieapp.data.model.ProductionCompanie
import barissaglam.client.movieapp.data.model.ProductionCountry
import barissaglam.client.movieapp.data.model.SpokenLanguage
import com.google.gson.annotations.SerializedName

data class MovieDetailReponse(
    @SerializedName("original_language") val originalLanguage: String?,
    @SerializedName("imdb_id") val imdbId: String?,
    @SerializedName("video") val video: Boolean?,
    @SerializedName("title") val title: String?,
    @SerializedName("backdrop_path") val backdropPath: String?,
    @SerializedName("revenue") val revenue: Int?,
    @SerializedName("genres") val genres: List<Genre>?,
    @SerializedName("popularity") val popularity: Double?,
    @SerializedName("production_countries") val productionCountries: List<ProductionCountry>?,
    @SerializedName("id") val id: Int?,
    @SerializedName("vote_count") val voteCount: Int?,
    @SerializedName("budget") val budget: Int?,
    @SerializedName("overview") val overview: String?,
    @SerializedName("original_title") val originalTitle: String?,
    @SerializedName("runtime") val runtime: Int?,
    @SerializedName("poster_path") val posterPath: String?,
    @SerializedName("spoken_languages") val spokenLanguages: List<SpokenLanguage>?,
    @SerializedName("production_companies") val productionCompanies: List<ProductionCompanie>?,
    @SerializedName("release_date") val releaseDate: String?,
    @SerializedName("vote_average") val voteAverage: Double?,
    @SerializedName("tagline") val tagline: String?,
    @SerializedName("adult") val adult: Boolean?,
    @SerializedName("homepage") val homepage: String?,
    @SerializedName("status") val status: String?
)