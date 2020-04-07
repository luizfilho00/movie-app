package br.com.devroid.data.entity

import br.com.devroid.domain.entity.Genre

data class ApiGenre(
    val id: Int,
    val name: String?
) {

    fun toGenre() = Genre(
        id = id,
        name = name ?: ""
    )
}