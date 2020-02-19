package br.com.jeramovies.domain.repository

import br.com.jeramovies.domain.entity.Movie
import br.com.jeramovies.domain.entity.MoviesResponse

interface MoviesRepository {

    suspend fun getMovies(page: Int? = null): List<Movie>
    suspend fun getMoviesResponse(page: Int? = null): MoviesResponse
    suspend fun searchMovies(text: String, page: Int): List<Movie>
}