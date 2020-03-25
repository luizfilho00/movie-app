package br.com.jeramovies.domain.entity

data class MoviesResponse(
    val page: Int,
    val totalResults: Int,
    val totalPages: Int,
    val movies: List<Movie>
)