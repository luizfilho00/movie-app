package br.com.jeramovies.domain.entity

import br.com.jeramovies.domain.util.IMAGE_URL
import br.com.jeramovies.domain.util.W500
import java.io.Serializable

data class Actor(
    val id: Long,
    val castIid: Long,
    val characterName: String,
    val creditId: String,
    val gender: Int,
    val name: String,
    val order: Int,
    val profilePath: String
) : Serializable {

    fun getProfileImage(size: String = W500) = "$IMAGE_URL$size/$profilePath"
}