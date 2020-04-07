package br.com.devroid.data.entity

import br.com.devroid.domain.entity.ProductionCountry
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