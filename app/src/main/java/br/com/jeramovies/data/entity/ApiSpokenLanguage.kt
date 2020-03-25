package br.com.jeramovies.data.entity

import br.com.jeramovies.domain.entity.SpokenLanguage
import com.google.gson.annotations.SerializedName

data class ApiSpokenLanguage(
    @SerializedName("iso_639_1") val iso_639_1: String?,
    @SerializedName("name") val name: String?
) {

    fun toSpokenLanguage() = SpokenLanguage(
        iso_639_1 = iso_639_1 ?: "",
        name = name ?: ""
    )
}