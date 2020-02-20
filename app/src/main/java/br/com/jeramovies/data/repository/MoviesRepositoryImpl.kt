package br.com.jeramovies.data.repository

import br.com.jeramovies.data.remote.ApiService
import br.com.jeramovies.domain.repository.MoviesRepository

class MoviesRepositoryImpl(
    private val apiService: ApiService
) : MoviesRepository {

    override suspend fun getMovies(page: Int) = apiService.getMovies(page)

    override suspend fun searchMovies(text: String, page: Int) = apiService.searchMovies(text, page)
}