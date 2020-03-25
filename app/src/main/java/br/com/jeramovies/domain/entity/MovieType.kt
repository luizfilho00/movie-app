package br.com.jeramovies.domain.entity

import java.io.Serializable

sealed class MovieType : Serializable {
    object PopularMovies : MovieType()
    object TopRatedMovies : MovieType()
    object InTheatersMovies : MovieType()
}