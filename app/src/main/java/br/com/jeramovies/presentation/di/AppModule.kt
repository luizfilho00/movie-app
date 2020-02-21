package br.com.jeramovies.presentation.di

import br.com.jeramovies.domain.resource.StringResource
import br.com.jeramovies.presentation.util.exceptionHandler.ExceptionHandler
import br.com.jeramovies.presentation.util.exceptionHandler.ExceptionHandlerImpl
import br.com.jeramovies.presentation.util.resource.AndroidStringResource
import org.koin.dsl.module

val appModule = module {

    single { ExceptionHandlerImpl(get()) as ExceptionHandler }
    single { AndroidStringResource(get()) as StringResource }
}