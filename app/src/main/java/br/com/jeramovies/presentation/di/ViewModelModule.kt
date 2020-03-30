package br.com.jeramovies.presentation.di

import br.com.jeramovies.domain.entity.MovieSaved
import br.com.jeramovies.presentation.ui.main.MainViewModel
import br.com.jeramovies.presentation.ui.movieDetails.MovieDetailsViewModel
import br.com.jeramovies.presentation.ui.movies.MoviesViewModel
import br.com.jeramovies.presentation.ui.myList.MyListViewModel
import br.com.jeramovies.presentation.ui.removeAlert.RemoveAlertViewModel
import br.com.jeramovies.presentation.ui.search.SearchViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {

    viewModel { MainViewModel() }
    viewModel { MoviesViewModel(get(), get(), get(), get(), get()) }
    viewModel { SearchViewModel(get(), get(), get()) }
    viewModel { MyListViewModel(get()) }
    viewModel { (id: Int) -> MovieDetailsViewModel(id, get(), get()) }
    viewModel { (movieSaved: MovieSaved) -> RemoveAlertViewModel(movieSaved, get(), get()) }
}