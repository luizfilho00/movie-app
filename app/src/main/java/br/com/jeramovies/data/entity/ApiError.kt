package br.com.jeramovies.data.entity

import com.google.gson.annotations.SerializedName

data class ApiError(
    @SerializedName("status_message") val message: String?,
    val errors: List<String>?
)