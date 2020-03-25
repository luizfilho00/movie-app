package br.com.jeramovies.data.entity.local

import br.com.jeramovies.domain.entity.Movie
import br.com.jeramovies.domain.entity.MovieSaved
import br.com.jeramovies.domain.util.W500
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

open class DbMovieSaved(
    @PrimaryKey
    var id: Int = 0,
    var title: String = "",
    var voteAverage: Double = 0.0,
    var posterUrl: String = ""
) : RealmObject() {

    fun toMovieSaved() = MovieSaved(
        id = id,
        title = title,
        voteAverage = voteAverage,
        posterUrl = posterUrl
    )

    companion object {
        const val FIELD_ID = "id"

        fun fromMovie(movie: Movie) = DbMovieSaved(
            id = movie.id,
            title = movie.title,
            voteAverage = movie.voteAverage,
            posterUrl = movie.getPosterUrl(W500)
        )
    }
}