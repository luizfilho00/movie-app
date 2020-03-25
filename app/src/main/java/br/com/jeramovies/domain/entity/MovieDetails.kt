package br.com.jeramovies.domain.entity

import br.com.jeramovies.domain.util.IMAGE_URL
import br.com.jeramovies.domain.util.W185
import br.com.jeramovies.domain.util.extensions.toDate
import org.joda.time.LocalDate
import java.text.NumberFormat
import java.util.*
import kotlin.math.roundToInt

data class MovieDetails(
    val id: Int,
    val adult: Boolean,
    val backdropPath: String,
    val belongsToCollection: Collection?,
    val budget: Int,
    val genres: List<Genre>,
    val homepage: String,
    val imdbId: String,
    val originalLanguage: String,
    val originalTitle: String,
    val overview: String,
    val popularity: Double,
    val posterPath: String,
    val productionCompanies: List<ProductionCompany>?,
    val productionCountries: List<ProductionCountry>?,
    val releaseDate: String?,
    val revenue: Int,
    val runtime: Int,
    val spokenLanguages: List<SpokenLanguage>?,
    val status: String,
    val tagline: String,
    val title: String,
    val video: Boolean,
    val voteAverage: Double,
    val voteCount: Int
) {

    fun date() = releaseDate?.toDate()?.let(LocalDate::fromDateFields) ?: LocalDate()

    fun userScore() = (voteAverage * 10.0).roundToInt().toString()

    fun durationHours() = runtime.let { (runtime / 60).toString() }

    fun durationMinutes() = runtime.let { (runtime % 60).toString() }

    fun budgetCost() = budget.toDouble().let { budgetDouble ->
        val numberFormat = NumberFormat.getCurrencyInstance(Locale.US)
        numberFormat.format(budgetDouble / 100.0)
    } ?: ""

    fun getPosterUrl(path: String?, size: String = W185) = "$IMAGE_URL$size/$path"
}