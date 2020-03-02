package br.com.jeramovies.data.remote

import br.com.jeramovies.domain.entity.MovieDetails
import br.com.jeramovies.domain.entity.MoviesResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @GET("movie/popular")
    suspend fun getPopularMovies(@Query("page") page: Int? = 1): MoviesResponse

    @GET("movie/top_rated")
    suspend fun getTopRatedMovies(@Query("page") page: Int? = 1): MoviesResponse

    @GET("movie/now_playing")
    suspend fun getNowPlayingMovies(@Query("page") page: Int? = 1): MoviesResponse


    @GET("movie/{id}")
    suspend fun getMovieDetails(@Path("id") id: Int): MovieDetails

    @GET("search/movie")
    suspend fun searchMovies(
        @Query("query") text: String,
        @Query("page") page: Int = 1
    ): MoviesResponse
}