package br.com.jeramovies.data.mappers

import br.com.jeramovies.data.entity.local.DbMovieSaved
import br.com.jeramovies.data.util.Mapper
import br.com.jeramovies.domain.entity.MovieSaved

class DbMovieSavedToMovieSaved : Mapper<DbMovieSaved, MovieSaved> {

    override fun transform(input: DbMovieSaved) = MovieSaved(
        id = input.id,
        title = input.title,
        voteAverage = input.voteAverage,
        posterUrl = input.posterUrl
    )
}