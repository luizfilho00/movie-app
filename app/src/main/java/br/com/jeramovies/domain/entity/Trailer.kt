package br.com.jeramovies.domain.entity

import java.io.Serializable

data class Trailer(
    val id: String,
    val key: String,
    val site: String?,
    val type: String?
) : Serializable {

    fun isYoutubeVideo() = site == YOUTUBE_SITE

    companion object {
        const val YOUTUBE_SITE = "YouTube"
    }
}