package br.com.jeramovies.domain.entity

import br.com.jeramovies.domain.util.IMAGE_URL
import br.com.jeramovies.domain.util.W185
import br.com.jeramovies.domain.util.extensions.toDate
import com.google.gson.annotations.SerializedName
import org.joda.time.LocalDate
import java.text.NumberFormat
import java.util.*
import kotlin.math.roundToInt

data class MovieDetails(
    @SerializedName("adult") val adult: Boolean?,
    @SerializedName("backdrop_path") val backdropPath: String?,
    @SerializedName("belongs_to_collection") val belongsToCollection: Collection?,
    @SerializedName("budget") val budget: Int?,
    @SerializedName("genres") val genres: List<Genre>?,
    @SerializedName("homepage") val homepage: String?,
    @SerializedName("id") val id: Int?,
    @SerializedName("imdb_id") val imdbId: String?,
    @SerializedName("original_language") val originalLanguage: String?,
    @SerializedName("original_title") val originalTitle: String?,
    @SerializedName("overview") val overview: String?,
    @SerializedName("popularity") val popularity: Double?,
    @SerializedName("poster_path") val posterPath: String?,
    @SerializedName("production_companies") val productionCompanies: List<ProductionCompany>?,
    @SerializedName("production_countries") val productionCountries: List<ProductionCountry>?,
    @SerializedName("release_date") val releaseDate: String?,
    @SerializedName("revenue") val revenue: Int?,
    @SerializedName("runtime") val runtime: Int?,
    @SerializedName("spoken_languages") val spokenLanguages: List<SpokenLanguage>?,
    @SerializedName("status") val status: String?,
    @SerializedName("tagline") val tagline: String?,
    @SerializedName("title") val title: String?,
    @SerializedName("video") val video: Boolean?,
    @SerializedName("vote_average") val voteAverage: Double?,
    @SerializedName("vote_count") val voteCount: Int?
) {

    fun date() = releaseDate?.toDate()?.let(LocalDate::fromDateFields) ?: LocalDate()
    fun userScore() = ((voteAverage ?: 0.0) * 10.0).roundToInt().toString()
    fun durationHours(): String = runtime?.let { (runtime / 60).toString() } ?: ""
    fun durationMinutes(): String = runtime?.let { (runtime % 60).toString() } ?: ""
    fun budgetCost(): String {
        return budget?.toDouble()?.let { budgetDouble ->
            val numberFormat = NumberFormat.getCurrencyInstance(Locale.US)
            numberFormat.format(budgetDouble / 100.0)
        } ?: ""
    }

    fun getPosterUrl(path: String?, size: String = W185) = "$IMAGE_URL$size/$path"
}