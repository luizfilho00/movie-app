package br.com.jeramovies.domain.repository

import br.com.jeramovies.domain.entity.MovieDetails
import br.com.jeramovies.domain.entity.MoviesResponse

interface MoviesRepository {

    suspend fun getMovies(page: Int): MoviesResponse
    suspend fun getMovieDetails(id: Int): MovieDetails
    suspend fun searchMovies(text: String, page: Int): MoviesResponse
}