package br.com.jeramovies.presentation.util.exceptionHandler

import android.content.Context
import br.com.jeramovies.R
import java.net.UnknownHostException

class ExceptionHandlerImpl(
    private val context: Context
) : ExceptionHandler {

    override fun resolveExceptionMessage(throwable: Throwable): String {
        return with(context) {
            when (throwable) {
                is UnknownHostException -> getString(R.string.error_network)
                else -> getString(R.string.unknown_error)
            }
        }
    }
}