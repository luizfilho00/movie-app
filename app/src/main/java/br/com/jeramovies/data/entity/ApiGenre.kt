package br.com.jeramovies.data.entity

import br.com.jeramovies.domain.entity.Genre

data class ApiGenre(
    val id: Int,
    val name: String?
) {

    fun toGenre() = Genre(
        id = id,
        name = name ?: ""
    )
}