package br.com.jeramovies.domain.entity

import com.google.gson.annotations.SerializedName

data class MoviesResponse(
    @SerializedName("page") val page: Int,
    @SerializedName("total_results") val totalResults: Int,
    @SerializedName("total_pages") val totalPages: Int,
    @SerializedName("results") val movies: List<Movie>
)