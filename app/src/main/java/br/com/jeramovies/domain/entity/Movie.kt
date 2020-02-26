package br.com.jeramovies.domain.entity

import br.com.jeramovies.domain.util.extensions.toDate
import com.google.gson.annotations.SerializedName
import org.joda.time.LocalDate
import kotlin.math.roundToInt

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
    @SerializedName("vote_average") val voteAverage: Double?,
    @SerializedName("overview") val overview: String,
    @SerializedName("release_date") val releaseDate: String?
) {

    fun userScore() = ((voteAverage ?: 0.0) * 10.0).roundToInt().toString()

    fun date() = releaseDate?.toDate()?.let(LocalDate::fromDateFields)

    fun getPosterUrl(size: String = W500) = "https://image.tmdb.org/t/p/$size/$backdropPath"

    companion object {
        const val DATE_PATTERN = "yyyy-MM-dd"
        const val W185 = "w185"
        const val W500 = "w500"
    }
}