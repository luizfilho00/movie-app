package br.com.jeramovies.data.entity

import br.com.jeramovies.domain.entity.MoviesResponse
import com.google.gson.annotations.SerializedName

data class ApiMoviesResponse(
    @SerializedName("page") val page: Int,
    @SerializedName("total_results") val totalResults: Int,
    @SerializedName("total_pages") val totalPages: Int,
    @SerializedName("results") val movies: List<ApiMovie>
) {

    fun toMovieResponse() = MoviesResponse(
        page = page,
        totalPages = totalPages,
        totalResults = totalResults,
        movies = movies.map { it.toMovie() }
    )
}