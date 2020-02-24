package br.com.jeramovies.domain.entity

import com.google.gson.annotations.SerializedName

data class SpokenLanguage(
    @SerializedName("iso_639_1") val iso_639_1: String?,
    @SerializedName("name") val name: String?
)