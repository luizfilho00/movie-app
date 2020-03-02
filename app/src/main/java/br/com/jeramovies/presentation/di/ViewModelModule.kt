package br.com.jeramovies.presentation.di

import br.com.jeramovies.presentation.ui.main.MoviesViewModel
import br.com.jeramovies.presentation.ui.movie.detail.MovieDetailsViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {

    viewModel { MoviesViewModel(get()) }
    viewModel { (id: Int) -> MovieDetailsViewModel(id, get()) }
}