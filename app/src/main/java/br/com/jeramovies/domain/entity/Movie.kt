package br.com.jeramovies.domain.entity

import com.google.gson.annotations.SerializedName

data class Movie(
    @SerializedName("popularity") val popularity: Double,
    @SerializedName("vote_count") val voteCount: Int,
    @SerializedName("video") val video: Boolean,
    @SerializedName("poster_path") val posterPath: String,
    @SerializedName("id") val id: Int,
    @SerializedName("adult") val adult: Boolean,
    @SerializedName("backdrop_path") val backdropPath: String,
    @SerializedName("original_language") val originalLanguage: String,
    @SerializedName("original_title") val originalTitle: String,
    @SerializedName("genre_ids") val genreIds: List<Int>,
    @SerializedName("title") val title: String,
    @SerializedName("vote_average") val voteAverage: Double,
    @SerializedName("overview") val overview: String,
    @SerializedName("release_date") val releaseDate: String
) {

    fun getPosterUrl(size: String = W185) = "https://image.tmdb.org/t/p/$size/$posterPath"

    companion object {
        const val W185 = "w185"
        const val W500 = "w500"
    }
}