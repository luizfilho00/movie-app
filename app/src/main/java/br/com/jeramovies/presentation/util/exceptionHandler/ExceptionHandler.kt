package br.com.jeramovies.presentation.util.exceptionHandler

interface ExceptionHandler {

    fun resolveExceptionMessage(throwable: Throwable): String
}