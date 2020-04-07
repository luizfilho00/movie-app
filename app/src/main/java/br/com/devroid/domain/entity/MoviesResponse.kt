package br.com.devroid.domain.entity

data class MoviesResponse(
    val page: Int,
    val totalResults: Int,
    val totalPages: Int,
    val movies: List<Movie>
)