package br.com.jeramovies.data.mappers

import br.com.jeramovies.data.entity.local.DbMovieSaved
import br.com.jeramovies.data.util.Mapper
import br.com.jeramovies.domain.entity.Movie
import br.com.jeramovies.domain.util.W500

class MovieToDbMovieSaved : Mapper<Movie, DbMovieSaved> {

    override fun transform(input: Movie) = DbMovieSaved(
        id = input.id,
        title = input.title,
        voteAverage = input.voteAverage,
        posterUrl = input.getPosterUrl(W500)
    )
}