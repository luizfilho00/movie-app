package br.com.jeramovies.domain.entity

import com.google.gson.annotations.SerializedName

data class Error(
    @SerializedName("status_message") val message: String?,
    val errors: List<String>?
)