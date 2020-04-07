package br.com.devroid.data.entity.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import br.com.devroid.domain.entity.Movie
import br.com.devroid.domain.entity.MovieSaved
import br.com.devroid.domain.util.W500

@Entity(tableName = "DbMovieSaved")
data class DbMovieSaved(
    @PrimaryKey val id: Int,
    val title: String,
    val voteAverage: Double,
    val posterUrl: String
) {

    fun toMovieSaved() = MovieSaved(
        id = id,
        title = title,
        voteAverage = voteAverage,
        posterUrl = posterUrl
    )

    companion object {
        fun fromMovie(movie: Movie) = DbMovieSaved(
            id = movie.id,
            title = movie.title,
            voteAverage = movie.voteAverage,
            posterUrl = movie.getPosterUrl(W500)
        )
    }
}