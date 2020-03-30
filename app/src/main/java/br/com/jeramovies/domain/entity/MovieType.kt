package br.com.jeramovies.domain.entity

import java.io.Serializable

sealed class MovieType : Serializable {
    object PopularMovies : MovieType()
    object TopRatedMovies : MovieType()
    object InTheatersMovies : MovieType()

    companion object {
        const val MOVIE_TYPE_POPULAR = 0
        const val MOVIE_TYPE_IN_THEATERS = 1
        const val MOVIE_TYPE_TOP_RATED = 2
    }
}