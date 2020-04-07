package br.com.devroid.presentation.di

import br.com.devroid.data.MovieAppDatabase
import br.com.devroid.domain.resource.StringResource
import br.com.devroid.presentation.ui.movies.MoviesObserver
import br.com.devroid.presentation.ui.movies.MoviesObserverImpl
import br.com.devroid.presentation.util.exceptionHandler.ExceptionHandler
import br.com.devroid.presentation.util.exceptionHandler.ExceptionHandlerImpl
import br.com.devroid.presentation.util.resource.AndroidStringResource
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