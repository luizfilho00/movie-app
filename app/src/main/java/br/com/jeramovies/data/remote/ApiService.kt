package br.com.jeramovies.data.remote

import br.com.jeramovies.domain.entity.MoviesResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("movie/popular")
    suspend fun getMovies(@Query("page") page: Int? = 1): MoviesResponse
}