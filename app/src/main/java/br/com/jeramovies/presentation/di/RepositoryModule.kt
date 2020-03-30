package br.com.jeramovies.presentation.di

import br.com.jeramovies.data.repository.*
import br.com.jeramovies.domain.repository.*
import org.koin.dsl.module

val repositoryModule = module {

    single { MoviesRepositoryImpl(get()) as MoviesRepository }
    single { MyListRepositoryImpl(get(), get(), get(), get()) as MyListRepository }
    single { PopularMoviesRepositoryImpl(get(), get(), get()) as PopularMoviesRepository }
    single { TopRatedMoviesRepositoryImpl(get(), get(), get()) as TopRatedMoviesRepository }
    single { InTheatersMoviesRepositoryImpl(get(), get(), get()) as InTheatersMoviesRepository }
}