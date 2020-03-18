package br.com.jeramovies.domain.entity

data class MovieSaved(
    val id: Int,
    val title: String,
    val voteAverage: Double?,
    val posterUrl: String
)