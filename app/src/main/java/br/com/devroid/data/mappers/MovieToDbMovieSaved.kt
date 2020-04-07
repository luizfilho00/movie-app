package br.com.devroid.data.mappers

import br.com.devroid.data.entity.local.DbMovieSaved
import br.com.devroid.data.util.Mapper
import br.com.devroid.domain.entity.Movie
import br.com.devroid.domain.util.W500

class MovieToDbMovieSaved : Mapper<Movie, DbMovieSaved> {

    override fun transform(input: Movie) = DbMovieSaved(
        id = input.id,
        title = input.title,
        voteAverage = input.voteAverage,
        posterUrl = input.getPosterUrl(W500)
    )
}