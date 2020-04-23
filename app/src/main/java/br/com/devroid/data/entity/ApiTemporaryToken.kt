package br.com.devroid.data.entity

import com.google.gson.annotations.SerializedName
import java.text.SimpleDateFormat
import java.util.*

data class ApiTemporaryToken(
    @SerializedName("guest_session_id")
    val sessionId: String,
    @SerializedName("success")
    val success: Boolean,
    @SerializedName("expires_at")
    val expiresAt: String
) {

    fun isValid(): Boolean {
        val expiresAtWithoutUTC = expiresAt.removeSuffix(" UTC")
        val date =
            SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).parse(expiresAtWithoutUTC)
        return date?.after(Date()) ?: false
    }
}