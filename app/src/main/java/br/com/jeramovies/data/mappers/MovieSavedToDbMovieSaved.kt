package br.com.jeramovies.data.mappers

import br.com.jeramovies.data.entity.local.DbMovieSaved
import br.com.jeramovies.data.util.Mapper
import br.com.jeramovies.domain.entity.MovieSaved

class MovieSavedToDbMovieSaved : Mapper<MovieSaved, DbMovieSaved> {

    override fun transform(input: MovieSaved) = DbMovieSaved(
        id = input.id,
        title = input.title,
        voteAverage = input.voteAverage,
        posterUrl = input.posterUrl
    )
}