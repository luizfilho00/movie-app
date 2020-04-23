package br.com.devroid.domain.entity

import com.google.gson.annotations.SerializedName

data class MovieRatingResponse(
    @SerializedName("status_code") val statusCode: Int,
    @SerializedName("status_message") val statusMessage: String
)