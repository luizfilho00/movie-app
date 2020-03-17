package br.com.jeramovies.data.repository

import br.com.jeramovies.data.remote.ApiService
import br.com.jeramovies.domain.repository.MoviesRepository

class MoviesRepositoryImpl(
    private val apiService: ApiService
) : MoviesRepository {

    override suspend fun getPopularMovies(page: Int) = apiService.getPopularMovies(page)

    override suspend fun getTopRatedMovies(page: Int) = apiService.getTopRatedMovies(page)

    override suspend fun getNowPlayingMovies(page: Int) = apiService.getNowPlayingMovies(page)

    override suspend fun getMovieDetails(id: Int) = apiService.getMovieDetails(id)

    override suspend fun searchMovies(text: String, page: Int) = apiService.searchMovies(text, page)

    override suspend fun getMovieCrew(id: Int) = apiService.getMovieCrew(id)

    override suspend fun getTrailers(id: Int, language: String) =
        apiService.getMovieTrailers(id, language)
}