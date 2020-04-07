package br.com.devroid.data.entity

import br.com.devroid.domain.entity.Trailer
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