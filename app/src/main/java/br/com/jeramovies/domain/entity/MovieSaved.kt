package br.com.jeramovies.domain.entity

import java.io.Serializable

data class MovieSaved(
    val id: Int,
    val title: String,
    val voteAverage: Double,
    val posterUrl: String
) : Serializable