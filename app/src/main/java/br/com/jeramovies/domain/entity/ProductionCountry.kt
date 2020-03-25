package br.com.jeramovies.domain.entity

import java.io.Serializable

data class ProductionCountry(
    val iso_3166_1: String,
    val name: String
) : Serializable