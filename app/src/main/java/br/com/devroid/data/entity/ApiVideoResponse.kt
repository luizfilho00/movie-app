package br.com.devroid.data.entity

import br.com.devroid.domain.entity.VideoResponse

data class ApiVideoResponse(
    val id: Int,
    val results: List<ApiTrailer>?
) {

    fun toVideoResponse() = VideoResponse(
        id = id,
        results = results?.map { it.toTrailer() } ?: listOf()
    )
}