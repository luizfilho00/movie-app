package br.com.jeramovies.presentation.di

import br.com.jeramovies.data.repository.MoviesRepositoryImpl
import br.com.jeramovies.data.repository.MyListRepositoryImpl
import br.com.jeramovies.domain.repository.MoviesRepository
import br.com.jeramovies.domain.repository.MyListRepository
import org.koin.dsl.module

val repositoryModule = module {

    single { MoviesRepositoryImpl(get()) as MoviesRepository }
    single { MyListRepositoryImpl() as MyListRepository }
}