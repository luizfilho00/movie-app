package br.com.devroid.data.mappers

import br.com.devroid.data.entity.local.DbInTheatersMovie
import br.com.devroid.data.util.Mapper
import br.com.devroid.domain.entity.Movie
import br.com.devroid.domain.entity.MovieType

class MovieToDbInTheatersMovie(
    private val sequenceId: Int? = null
) : Mapper<Movie, DbInTheatersMovie> {

    override fun transform(input: Movie) = DbInTheatersMovie(
        id = input.id,
        popularity = input.popularity,
        voteCount = input.voteCount,
        video = input.video,
        posterPath = input.posterPath,
        adult = input.adult,
        backdropPath = input.backdropPath,
        originalLanguage = input.originalLanguage,
        originalTitle = input.originalTitle,
        genreIds = input.genreIds,
        title = input.title,
        voteAverage = input.voteAverage,
        overview = input.overview,
        releaseDate = input.releaseDate,
        saved = input.saved,
        sequenceId = sequenceId ?: input.sequenceId,
        movieType = MovieType.MOVIE_TYPE_IN_THEATERS
    )
}