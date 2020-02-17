package br.com.jeramovies.data.repository

import br.com.jeramovies.data.remote.ApiService
import br.com.jeramovies.domain.MoviesRepository

class MoviesRepositoryImpl(
    private val apiService: ApiService
) : MoviesRepository {

    override suspend fun getMovies() = apiService.getMovies().movies
}