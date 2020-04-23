package br.com.devroid.data.mappers

import br.com.devroid.data.entity.local.DbMovieSaved
import br.com.devroid.data.util.Mapper
import br.com.devroid.domain.entity.MovieDetails
import br.com.devroid.domain.util.W500

class MovieDetailsToDbMovieSaved : Mapper<MovieDetails, DbMovieSaved> {

    override fun transform(input: MovieDetails) = DbMovieSaved(
        id = input.id,
        title = input.title,
        voteAverage = input.voteAverage,
        posterUrl = input.getPosterUrl(input.posterPath, W500)
    )
}