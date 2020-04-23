package br.com.devroid.domain.repository

import br.com.devroid.domain.entity.MovieCast
import br.com.devroid.domain.entity.MovieDetails
import br.com.devroid.domain.entity.MoviesResponse
import br.com.devroid.domain.entity.VideoResponse
import br.com.devroid.domain.util.PT_BR

interface MoviesRepository {

    suspend fun getRecommendations(id: Int, page: Int): MoviesResponse
    suspend fun getMovieDetails(id: Int): MovieDetails?
    suspend fun searchMovies(text: String, page: Int): MoviesResponse
    suspend fun getMovieCrew(id: Int): MovieCast
    suspend fun getTrailers(id: Int, language: String = PT_BR): VideoResponse
}