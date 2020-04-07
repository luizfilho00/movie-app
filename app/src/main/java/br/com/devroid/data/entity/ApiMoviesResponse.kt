package br.com.devroid.data.entity

import br.com.devroid.domain.entity.MovieType
import br.com.devroid.domain.entity.MoviesResponse
import com.google.gson.annotations.SerializedName

data class ApiMoviesResponse(
    @SerializedName("page") val page: Int,
    @SerializedName("total_results") val totalResults: Int,
    @SerializedName("total_pages") val totalPages: Int,
    @SerializedName("results") val movies: List<ApiMovie>
) {

    fun toMovieResponse(movieType: MovieType? = null) = MoviesResponse(
        page = page,
        totalPages = totalPages,
        totalResults = totalResults,
        movies = movies.map { apimovie ->
            movieType?.let {
                apimovie.toMovie().apply { type = it }
            } ?: apimovie.toMovie()
        }
    )
}