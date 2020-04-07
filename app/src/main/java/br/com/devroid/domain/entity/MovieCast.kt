package br.com.devroid.domain.entity

import java.io.Serializable

data class MovieCast(
    val id: Long,
    val cast: List<Actor>
) : Serializable