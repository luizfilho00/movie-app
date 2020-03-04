package br.com.jeramovies.presentation.di

import br.com.jeramovies.presentation.ui.main.MainViewModel
import br.com.jeramovies.presentation.ui.main.movies.MoviesViewModel
import br.com.jeramovies.presentation.ui.movie.detail.MovieDetailsViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {

    viewModel { MainViewModel(get()) }
    viewModel { MoviesViewModel(get()) }
    viewModel { (id: Int) -> MovieDetailsViewModel(id, get()) }
}