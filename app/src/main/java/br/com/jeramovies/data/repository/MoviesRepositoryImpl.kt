package br.com.jeramovies.data.repository

import br.com.jeramovies.data.remote.ApiService
import br.com.jeramovies.domain.repository.MoviesRepository

class MoviesRepositoryImpl(
    private val apiService: ApiService
) : MoviesRepository {

    override suspend fun getMovieDetails(id: Int) = apiService.getMovieDetails(id).toMovieDetails()

    override suspend fun searchMovies(text: String, page: Int) =
        apiService.searchMovies(text, page).toMovieResponse()

    override suspend fun getRecommendations(id: Int, page: Int) =
        apiService.getRecommendations(id, page).toMovieResponse()

    override suspend fun getMovieCrew(id: Int) = apiService.getMovieCrew(id).toMovieCast()

    override suspend fun getTrailers(id: Int, language: String) =
        apiService.getMovieTrailers(id, language).toVideoResponse()
}