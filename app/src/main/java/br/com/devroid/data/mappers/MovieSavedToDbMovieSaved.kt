package br.com.devroid.data.mappers

import br.com.devroid.data.entity.local.DbMovieSaved
import br.com.devroid.data.util.Mapper
import br.com.devroid.domain.entity.MovieSaved

class MovieSavedToDbMovieSaved : Mapper<MovieSaved, DbMovieSaved> {

    override fun transform(input: MovieSaved) = DbMovieSaved(
        id = input.id,
        title = input.title,
        voteAverage = input.voteAverage,
        posterUrl = input.posterUrl
    )
}