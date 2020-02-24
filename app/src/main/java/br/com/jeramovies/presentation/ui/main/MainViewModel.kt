package br.com.jeramovies.presentation.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.DataSource
import androidx.paging.PagedList
import androidx.paging.toLiveData
import br.com.jeramovies.data.paging.dataSource.MoviesDataSource
import br.com.jeramovies.data.paging.factory.SearchMoviesDataSourceFactory
import br.com.jeramovies.domain.entity.Movie
import br.com.jeramovies.domain.repository.MoviesRepository
import br.com.jeramovies.presentation.ui.movie.detail.MovieDetailsNavData
import br.com.jeramovies.presentation.util.base.BaseViewModel

class MainViewModel(
    private val repository: MoviesRepository
) : BaseViewModel() {

    val movies: LiveData<PagedList<Movie>>
    val searchMovies: LiveData<PagedList<Movie>>

    private val config = PagedList.Config.Builder()
        .setEnablePlaceholders(true)
        .setPageSize(30)
        .build()

    private val dataSourceFactory =
        SearchMoviesDataSourceFactory(
            repository,
            viewModelScope,
            onFailure = { showDialog(it) }
        )

    private val movieFactory = object : DataSource.Factory<Int, Movie>() {
        override fun create(): DataSource<Int, Movie> {
            return MoviesDataSource(
                repository,
                viewModelScope,
                onFailure = { showDialog(it) }
            )
        }
    }
    private val searchMovieFactory = object : DataSource.Factory<Int, Movie>() {
        override fun create(): DataSource<Int, Movie> {
            return dataSourceFactory.create()
        }
    }

    init {
        movies = movieFactory.toLiveData(config)
        searchMovies = searchMovieFactory.toLiveData(config)
    }

    fun searchMovies(text: String) {
        dataSourceFactory.text = text
        reloadSearch()
    }

    fun onMovieClick(movie: Movie) {
        goTo(MovieDetailsNavData(movie.id))
    }

    private fun reloadSearch() {
        searchMovies.value?.dataSource?.invalidate()
    }
}