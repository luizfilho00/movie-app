package br.com.devroid.presentation.di

import br.com.devroid.data.repository.*
import br.com.devroid.domain.repository.*
import org.koin.dsl.module

val repositoryModule = module {

    single { MoviesRepositoryImpl(get(), get(), get()) as MoviesRepository }
    single { MyListRepositoryImpl(get(), get(), get(), get(), get()) as MyListRepository }
    single { PopularMoviesRepositoryImpl(get(), get(), get()) as PopularMoviesRepository }
    single { TopRatedMoviesRepositoryImpl(get(), get(), get()) as TopRatedMoviesRepository }
    single { InTheatersMoviesRepositoryImpl(get(), get(), get()) as InTheatersMoviesRepository }
}