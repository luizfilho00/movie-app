package br.com.jeramovies.data.entity

import br.com.jeramovies.domain.entity.ProductionCountry
import com.google.gson.annotations.SerializedName

data class ApiProductionCountry(
    @SerializedName("iso_3166_1") val iso_3166_1: String?,
    @SerializedName("name") val name: String?
) {

    fun toProductionCountry() = ProductionCountry(
        iso_3166_1 = iso_3166_1 ?: "",
        name = name ?: ""
    )
}