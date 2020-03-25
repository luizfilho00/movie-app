package br.com.jeramovies.data.entity

import br.com.jeramovies.domain.entity.Trailer
import java.io.Serializable

data class ApiTrailer(
    val id: String,
    val key: String,
    val site: String?,
    val type: String?
) : Serializable {

    fun toTrailer() = Trailer(
        id = id,
        key = key,
        site = site ?: "",
        type = type ?: ""
    )
}