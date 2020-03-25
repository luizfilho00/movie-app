package br.com.jeramovies.domain.entity

import java.io.Serializable

data class VideoResponse(
    val id: Int,
    val results: List<Trailer>
) : Serializable