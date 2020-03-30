package br.com.jeramovies.data.entity.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import br.com.jeramovies.domain.entity.Movie
import br.com.jeramovies.domain.entity.MovieType

@Entity(tableName = "DbTopRatedMovie")
data class DbTopRatedMovie(
    override val id: Int,
    @PrimaryKey override val sequenceId: Int,
    override val popularity: Double,
    override val voteCount: Int,
    override val video: Boolean,
    override val posterPath: String?,
    override val adult: Boolean,
    override val backdropPath: String?,
    override val originalLanguage: String?,
    override val originalTitle: String?,
    override val genreIds: List<Int>,
    override val title: String,
    override val voteAverage: Double,
    override val overview: String?,
    override val releaseDate: String?,
    override val movieType: Int,
    override val saved: Boolean
) : DbMovie {

    override fun fromMovie(movie: Movie) =
        DbPopularMovie(
            id = movie.id,
            popularity = movie.popularity,
            voteCount = movie.voteCount,
            video = movie.video,
            posterPath = movie.posterPath,
            adult = movie.adult,
            backdropPath = movie.backdropPath,
            originalLanguage = movie.originalLanguage,
            originalTitle = movie.originalTitle,
            genreIds = movie.genreIds,
            title = movie.title,
            voteAverage = movie.voteAverage,
            overview = movie.overview,
            releaseDate = movie.releaseDate,
            saved = movie.saved,
            sequenceId = movie.sequenceId,
            movieType = MovieType.MOVIE_TYPE_TOP_RATED
        )
}