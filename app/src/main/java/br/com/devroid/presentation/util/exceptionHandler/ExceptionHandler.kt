package br.com.devroid.presentation.util.exceptionHandler

interface ExceptionHandler {

    fun resolveExceptionMessage(throwable: Throwable): String
}