package br.com.devroid.data.mappers

import br.com.devroid.data.entity.local.DbMovieSaved
import br.com.devroid.data.util.Mapper
import br.com.devroid.domain.entity.MovieSaved

class DbMovieSavedToMovieSaved : Mapper<DbMovieSaved, MovieSaved> {

    override fun transform(input: DbMovieSaved) = MovieSaved(
        id = input.id,
        title = input.title,
        voteAverage = input.voteAverage,
        posterUrl = input.posterUrl
    )
}