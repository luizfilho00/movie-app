package br.com.devroid.domain.entity

import java.io.Serializable

data class Collection(
    val id: Int,
    val name: String,
    val posterPath: String,
    val backdropPath: String
) : Serializable