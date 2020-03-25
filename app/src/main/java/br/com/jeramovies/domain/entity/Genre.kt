package br.com.jeramovies.domain.entity

import java.io.Serializable

data class Genre(
    val id: Int,
    val name: String
) : Serializable