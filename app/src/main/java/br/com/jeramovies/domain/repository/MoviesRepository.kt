package br.com.jeramovies.domain.repository

import br.com.jeramovies.domain.entity.MoviesResponse

interface MoviesRepository {

    suspend fun getMovies(page: Int? = null): MoviesResponse
    suspend fun searchMovies(text: String, page: Int): MoviesResponse
}