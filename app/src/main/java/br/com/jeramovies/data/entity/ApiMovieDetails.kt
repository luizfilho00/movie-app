package br.com.jeramovies.data.entity

import br.com.jeramovies.domain.entity.MovieDetails
import com.google.gson.annotations.SerializedName

data class ApiMovieDetails(
    @SerializedName("id") val id: Int,
    @SerializedName("adult") val adult: Boolean?,
    @SerializedName("backdrop_path") val backdropPath: String?,
    @SerializedName("belongs_to_collection") val belongsToCollection: ApiCollection?,
    @SerializedName("budget") val budget: Int?,
    @SerializedName("genres") val genres: List<ApiGenre>?,
    @SerializedName("homepage") val homepage: String?,
    @SerializedName("imdb_id") val imdbId: String?,
    @SerializedName("original_language") val originalLanguage: String?,
    @SerializedName("original_title") val originalTitle: String?,
    @SerializedName("overview") val overview: String?,
    @SerializedName("popularity") val popularity: Double?,
    @SerializedName("poster_path") val posterPath: String?,
    @SerializedName("production_companies") val productionCompanies: List<ApiProductionCompany>?,
    @SerializedName("production_countries") val productionCountries: List<ApiProductionCountry>?,
    @SerializedName("release_date") val releaseDate: String?,
    @SerializedName("revenue") val revenue: Int?,
    @SerializedName("runtime") val runtime: Int?,
    @SerializedName("spoken_languages") val spokenLanguages: List<ApiSpokenLanguage>?,
    @SerializedName("status") val status: String?,
    @SerializedName("tagline") val tagline: String?,
    @SerializedName("title") val title: String?,
    @SerializedName("video") val video: Boolean?,
    @SerializedName("vote_average") val voteAverage: Double?,
    @SerializedName("vote_count") val voteCount: Int?
) {

    fun toMovieDetails() = MovieDetails(
        adult = adult ?: false,
        backdropPath = backdropPath ?: "",
        belongsToCollection = belongsToCollection?.toCollection(),
        budget = budget ?: 0,
        genres = genres?.map { it.toGenre() } ?: listOf(),
        homepage = homepage ?: "",
        id = id,
        imdbId = imdbId ?: "",
        originalLanguage = originalLanguage ?: "",
        originalTitle = originalTitle ?: "",
        overview = overview ?: "",
        popularity = popularity ?: 0.0,
        posterPath = posterPath ?: "",
        productionCompanies = productionCompanies?.map { it.toProductionCompany() },
        productionCountries = productionCountries?.map { it.toProductionCountry() },
        releaseDate = releaseDate,
        revenue = revenue ?: 0,
        runtime = runtime ?: 0,
        spokenLanguages = spokenLanguages?.map { it.toSpokenLanguage() },
        status = status ?: "",
        tagline = tagline ?: "",
        title = title ?: "",
        video = video ?: false,
        voteAverage = voteAverage ?: 0.0,
        voteCount = 0
    )
}