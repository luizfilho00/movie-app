package br.com.jeramovies.domain

import br.com.jeramovies.data.entity.Movie

interface MoviesRepository {

    suspend fun getMovies(): List<Movie>
}