package br.com.devroid.domain.entity

import java.io.Serializable

data class VideoResponse(
    val id: Int,
    val results: List<Trailer>
) : Serializable