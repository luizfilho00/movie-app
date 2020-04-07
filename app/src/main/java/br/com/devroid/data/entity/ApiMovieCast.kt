package br.com.devroid.data.entity

import br.com.devroid.domain.entity.MovieCast
import java.io.Serializable

data class ApiMovieCast(
    val id: Long,
    val cast: List<ApiActor>?
) : Serializable {

    fun toMovieCast() = MovieCast(
        id = id,
        cast = cast?.map { apiActor -> apiActor.toActor() } ?: listOf()
    )
}