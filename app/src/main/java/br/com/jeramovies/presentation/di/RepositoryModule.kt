package br.com.jeramovies.presentation.di

import br.com.jeramovies.data.repository.*
import br.com.jeramovies.domain.repository.*
import org.koin.dsl.module

val repositoryModule = module {

    single { MoviesRepositoryImpl(get()) as MoviesRepository }
    single { MyListRepositoryImpl(get()) as MyListRepository }
    single { PopularMoviesRepositoryImpl(get(), get()) as PopularMoviesRepository }
    single { TopRatedMoviesRepositoryImpl(get(), get()) as TopRatedMoviesRepository }
    single { InTheatersMoviesRepositoryImpl(get(), get()) as InTheatersMoviesRepository }
}