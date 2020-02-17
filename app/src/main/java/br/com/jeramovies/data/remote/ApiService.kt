package br.com.jeramovies.data.remote

import br.com.jeramovies.domain.entity.MoviesResponse
import retrofit2.http.GET

interface ApiService {

    @GET("movie/popular")
    suspend fun getMovies(): MoviesResponse
}