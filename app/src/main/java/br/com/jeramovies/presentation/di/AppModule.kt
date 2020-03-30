package br.com.jeramovies.presentation.di

import br.com.jeramovies.data.MovieAppDatabase
import br.com.jeramovies.domain.resource.StringResource
import br.com.jeramovies.presentation.ui.movies.MoviesObserver
import br.com.jeramovies.presentation.ui.movies.MoviesObserverImpl
import br.com.jeramovies.presentation.util.exceptionHandler.ExceptionHandler
import br.com.jeramovies.presentation.util.exceptionHandler.ExceptionHandlerImpl
import br.com.jeramovies.presentation.util.resource.AndroidStringResource
import org.koin.dsl.module

val appModule = module {

    single { ExceptionHandlerImpl(get()) as ExceptionHandler }
    single { AndroidStringResource(get()) as StringResource }
    single { MoviesObserverImpl() as MoviesObserver }
    single { MovieAppDatabase.build(get()) }
    single { get<MovieAppDatabase>().inTheatersMoviesDao() }
    single { get<MovieAppDatabase>().topRatedMoviesDao() }
    single { get<MovieAppDatabase>().popularMoviesDao() }
    single { get<MovieAppDatabase>().pageLoadedDao() }
    single { get<MovieAppDatabase>().moviesDao() }
}