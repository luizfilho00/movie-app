package br.com.jeramovies.presentation.util.exceptionHandler

import android.content.Context
import br.com.jeramovies.R
import com.google.gson.Gson
import com.google.gson.annotations.SerializedName
import okhttp3.ResponseBody
import retrofit2.HttpException
import java.net.UnknownHostException

class ExceptionHandlerImpl(
    private val context: Context
) : ExceptionHandler {

    override fun resolveExceptionMessage(throwable: Throwable): String {
        return with(context) {
            when (throwable) {
                is UnknownHostException -> getString(R.string.error_network)
                is HttpException -> extractError(throwable.response()?.errorBody())
                    ?: getString(R.string.unknown_error)
                else -> getString(R.string.unknown_error)
            }
        }
    }

    private fun extractError(body: ResponseBody?): String? {
        return body?.let {
            toApiErrors(Gson().fromJson(body.string(), ApiError::class.java))
        }
    }

    private fun toApiErrors(apiError: ApiError): String? {
        return if (!apiError.errors.isNullOrEmpty()) {
            apiError.errors.joinToString("\n")
        } else {
            apiError.message
        }
    }

    data class ApiError(
        @SerializedName("status_message") val message: String?,
        val errors: List<String>?
    )
}