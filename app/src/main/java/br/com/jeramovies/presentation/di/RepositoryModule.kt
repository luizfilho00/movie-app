package br.com.jeramovies.presentation.di

import br.com.jeramovies.data.repository.MoviesRepositoryImpl
import br.com.jeramovies.domain.MoviesRepository
import org.koin.dsl.module

val repositoryModule = module {

    single { MoviesRepositoryImpl(get()) as MoviesRepository }
}