package br.com.jeramovies.domain.entity

import br.com.jeramovies.domain.util.IMAGE_URL
import br.com.jeramovies.domain.util.W185
import br.com.jeramovies.domain.util.W500
import br.com.jeramovies.domain.util.extensions.toDate
import org.joda.time.LocalDate
import java.io.Serializable
import kotlin.math.roundToInt

data class Movie(
    val popularity: Double,
    val voteCount: Int,
    val video: Boolean,
    val posterPath: String,
    val id: Int,
    val adult: Boolean,
    val backdropPath: String,
    val originalLanguage: String,
    val originalTitle: String,
    val genreIds: List<Int>,
    val title: String,
    val voteAverage: Double,
    val overview: String,
    val releaseDate: String?,
    val sequenceId: Int = 0,
    var saved: Boolean = false,
    var type: MovieType? = null
) : Serializable {

    fun userScore() = (voteAverage * 10.0).roundToInt().toString()

    fun date() = releaseDate?.toDate()?.let(LocalDate::fromDateFields)

    fun getBackdropUrl(size: String = W500) = "$IMAGE_URL$size/$backdropPath"

    fun getPosterUrl(size: String = W185) = "$IMAGE_URL$size/$posterPath"
}