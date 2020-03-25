package br.com.jeramovies.data.entity

import br.com.jeramovies.domain.entity.VideoResponse

data class ApiVideoResponse(
    val id: Int,
    val results: List<ApiTrailer>?
) {

    fun toVideoResponse() = VideoResponse(
        id = id,
        results = results?.map { it.toTrailer() } ?: listOf()
    )
}