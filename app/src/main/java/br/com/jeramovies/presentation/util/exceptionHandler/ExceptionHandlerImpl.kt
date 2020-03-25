package br.com.jeramovies.presentation.util.exceptionHandler

import android.content.Context
import br.com.jeramovies.R
import br.com.jeramovies.data.entity.ApiError
import com.google.gson.Gson
import okhttp3.ResponseBody
import retrofit2.HttpException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

class ExceptionHandlerImpl(
    private val context: Context
) : ExceptionHandler {

    override fun resolveExceptionMessage(throwable: Throwable): String {
        return with(context) {
            when (throwable) {
                is UnknownHostException -> getString(R.string.error_network)
                is HttpException -> extractError(throwable.response()?.errorBody())
                    ?: getString(R.string.http_error)
                is SocketTimeoutException -> getString(R.string.socket_timeout_error)
                else -> getString(R.string.unknown_error)
            }
        }
    }

    private fun extractError(body: ResponseBody?): String? {
        return body?.let {
            toApiErrors(Gson().fromJson(body.string(), ApiError::class.java))
        }
    }

    private fun toApiErrors(errorEntity: ApiError): String? {
        return if (!errorEntity.errors.isNullOrEmpty()) {
            errorEntity.errors.joinToString("\n")
        } else {
            errorEntity.message
        }
    }
}