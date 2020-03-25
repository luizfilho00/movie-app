package br.com.jeramovies.data.remote

import br.com.jeramovies.data.entity.ApiMovieCast
import br.com.jeramovies.data.entity.ApiMovieDetails
import br.com.jeramovies.data.entity.ApiMoviesResponse
import br.com.jeramovies.data.entity.ApiVideoResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @GET("movie/popular")
    suspend fun getPopularMovies(@Query("page") page: Int? = 1): ApiMoviesResponse

    @GET("movie/top_rated")
    suspend fun getTopRatedMovies(@Query("page") page: Int? = 1): ApiMoviesResponse

    @GET("movie/now_playing")
    suspend fun getInTheatersMovies(@Query("page") page: Int? = 1): ApiMoviesResponse

    @GET("movie/{id}/recommendations")
    suspend fun getRecommendations(
        @Path("id") id: Int,
        @Query("page") page: Int? = 1
    ): ApiMoviesResponse

    @GET("movie/{id}")
    suspend fun getMovieDetails(@Path("id") id: Int): ApiMovieDetails

    @GET("movie/{id}/credits")
    suspend fun getMovieCrew(@Path("id") id: Int): ApiMovieCast

    @GET("movie/{id}/videos")
    suspend fun getMovieTrailers(
        @Path("id") id: Int,
        @Query("language") language: String
    ): ApiVideoResponse

    @GET("search/movie")
    suspend fun searchMovies(
        @Query("query") text: String,
        @Query("page") page: Int = 1
    ): ApiMoviesResponse
}