package br.com.jeramovies.domain.entity

data class VideoResponse(
    val id: Int?,
    val results: List<Trailer>?
)