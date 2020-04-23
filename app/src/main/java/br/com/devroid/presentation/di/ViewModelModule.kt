package br.com.devroid.presentation.di

import br.com.devroid.domain.entity.MovieSaved
import br.com.devroid.presentation.ui.main.MainViewModel
import br.com.devroid.presentation.ui.movieDetails.MovieDetailsViewModel
import br.com.devroid.presentation.ui.movies.MoviesViewModel
import br.com.devroid.presentation.ui.myList.MyListViewModel
import br.com.devroid.presentation.ui.removeAlert.RemoveAlertViewModel
import br.com.devroid.presentation.ui.search.SearchViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {

    viewModel { MainViewModel() }
    viewModel { MoviesViewModel(get(), get(), get(), get(), get()) }
    viewModel { SearchViewModel(get(), get(), get()) }
    viewModel { MyListViewModel(get()) }
    viewModel { (id: Int) -> MovieDetailsViewModel(id, get(), get(), get(), get(), get()) }
    viewModel { (movieSaved: MovieSaved) -> RemoveAlertViewModel(movieSaved, get(), get()) }
}