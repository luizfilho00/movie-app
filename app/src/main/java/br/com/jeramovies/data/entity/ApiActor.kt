package br.com.jeramovies.data.entity

import br.com.jeramovies.domain.entity.Actor
import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class ApiActor(
    @SerializedName("id") val id: Long,
    @SerializedName("cast_id") val castIid: Long?,
    @SerializedName("character") val characterName: String?,
    @SerializedName("credit_id") val creditId: String?,
    @SerializedName("gender") val gender: Int?,
    @SerializedName("name") val name: String?,
    @SerializedName("order") val order: Int?,
    @SerializedName("profile_path") val profilePath: String?
) : Serializable {

    fun toActor() = Actor(
        id = id,
        castIid = castIid ?: 0,
        characterName = characterName ?: "",
        creditId = creditId ?: "",
        gender = gender ?: 0,
        name = name ?: "",
        order = order ?: 0,
        profilePath = profilePath ?: ""
    )
}