package br.com.devroid.domain.entity

import java.io.Serializable

data class ProductionCompany(
    val id: Int,
    val logoPath: String,
    val name: String,
    val originCountry: String
) : Serializable