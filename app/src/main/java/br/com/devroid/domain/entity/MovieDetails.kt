package br.com.devroid.domain.entity

import androidx.annotation.DrawableRes
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import br.com.devroid.domain.util.IMAGE_URL
import br.com.devroid.domain.util.W185
import br.com.devroid.domain.util.extensions.toDate
import br.com.devroid.moviesapp.R
import org.joda.time.LocalDate
import java.text.NumberFormat
import java.util.*
import kotlin.math.roundToInt

@Entity(tableName = "DbMovieDetails")
data class MovieDetails(
    @PrimaryKey
    @ColumnInfo(name = "movieDetailsId")
    val id: Int,
    val adult: Boolean,
    val backdropPath: String,
    val budget: Int,
    var genres: List<Genre>,
    val homepage: String,
    val imdbId: String,
    val originalLanguage: String,
    val originalTitle: String,
    val overview: String,
    val popularity: Double,
    val posterPath: String,
    val releaseDate: String?,
    val revenue: Int,
    val runtime: Int,
    val status: String,
    val tagline: String,
    val title: String,
    val video: Boolean,
    val voteAverage: Double,
    val voteCount: Int,
    var saved: Boolean = false,
    var rating: Float? = null
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

    @DrawableRes
    fun getRatingImage(): Int {
        return when {
            rating == null -> R.drawable.ic_thumb_up
            rating!! >= 5f -> R.drawable.ic_thumb_up_green
            else -> R.drawable.ic_thumb_down_red
        }
    }
}