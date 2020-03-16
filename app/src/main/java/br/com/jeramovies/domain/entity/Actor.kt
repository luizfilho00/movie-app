package br.com.jeramovies.domain.entity

import br.com.jeramovies.domain.util.IMAGE_URL
import br.com.jeramovies.domain.util.W500
import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Actor(
    @SerializedName("id") val id: Long?,
    @SerializedName("cast_id") val castIid: Long?,
    @SerializedName("character") val characterName: String?,
    @SerializedName("credit_id") val creditId: String?,
    @SerializedName("gender") val gender: Int?,
    @SerializedName("name") val name: String?,
    @SerializedName("order") val order: Int?,
    @SerializedName("profile_path") val profilePath: String?
) : Serializable {

    fun getProfileImage(size: String = W500) = "$IMAGE_URL$size/$profilePath"
}