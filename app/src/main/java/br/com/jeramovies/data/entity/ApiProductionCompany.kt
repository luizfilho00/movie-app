package br.com.jeramovies.data.entity

import br.com.jeramovies.domain.entity.ProductionCompany
import com.google.gson.annotations.SerializedName

data class ApiProductionCompany(
    @SerializedName("id") val id: Int,
    @SerializedName("logo_path") val logoPath: String?,
    @SerializedName("name") val name: String?,
    @SerializedName("origin_country") val originCountry: String?
) {

    fun toProductionCompany() = ProductionCompany(
        id = id,
        logoPath = logoPath ?: "",
        name = name ?: "",
        originCountry = originCountry ?: ""
    )
}