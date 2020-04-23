package br.com.devroid.data.remote

import br.com.devroid.data.entity.*
import br.com.devroid.domain.entity.MovieRating
import br.com.devroid.domain.entity.MovieRatingResponse
import retrofit2.http.*

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

    @POST("movie/{movieId}/rating")
    suspend fun rateMovie(
        @Path("movieId") movieId: Int,
        @Body rating: MovieRating,
        @Query("guest_session_id") token: String?
    ): MovieRatingResponse

    @GET("authentication/guest_session/new")
    suspend fun getToken(): ApiTemporaryToken
}