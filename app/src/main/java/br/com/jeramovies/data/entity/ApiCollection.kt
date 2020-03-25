package br.com.jeramovies.data.entity

import br.com.jeramovies.domain.entity.Collection
import com.google.gson.annotations.SerializedName

data class ApiCollection(
    @SerializedName("id") val id: Int,
    @SerializedName("name") val name: String?,
    @SerializedName("poster_path") val posterPath: String?,
    @SerializedName("backdrop_path") val backdropPath: String?
) {

    fun toCollection() = Collection(
        id = id,
        name = name ?: "",
        posterPath = posterPath ?: "",
        backdropPath = backdropPath ?: ""
    )
}