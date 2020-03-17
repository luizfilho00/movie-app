package br.com.jeramovies.domain.repository

import br.com.jeramovies.domain.entity.MovieCast
import br.com.jeramovies.domain.entity.MovieDetails
import br.com.jeramovies.domain.entity.MoviesResponse
import br.com.jeramovies.domain.entity.VideoResponse
import br.com.jeramovies.domain.util.PT_BR

interface MoviesRepository {

    suspend fun getPopularMovies(page: Int): MoviesResponse
    suspend fun getTopRatedMovies(page: Int): MoviesResponse
    suspend fun getNowPlayingMovies(page: Int): MoviesResponse
    suspend fun getRecommendations(id: Int, page: Int): MoviesResponse
    suspend fun getMovieDetails(id: Int): MovieDetails
    suspend fun searchMovies(text: String, page: Int): MoviesResponse
    suspend fun getMovieCrew(id: Int): MovieCast
    suspend fun getTrailers(id: Int, language: String = PT_BR): VideoResponse
}