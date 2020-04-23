package br.com.devroid.data.entity.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import br.com.devroid.domain.entity.Movie
import br.com.devroid.domain.entity.MovieType

@Entity(tableName = "DbPopularMovie")
data class DbPopularMovie(
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
    override val saved: Boolean,
    override var rateByUser: Float?
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
            rateByUser = movie.rateByUser,
            sequenceId = movie.sequenceId,
            movieType = MovieType.MOVIE_TYPE_POPULAR
        )
}