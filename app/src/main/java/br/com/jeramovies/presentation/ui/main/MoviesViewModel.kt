package br.com.jeramovies.presentation.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.DataSource
import androidx.paging.PagedList
import androidx.paging.toLiveData
import br.com.jeramovies.data.paging.dataSource.NowPlayingMoviesDataSource
import br.com.jeramovies.data.paging.dataSource.PopularMoviesDataSource
import br.com.jeramovies.data.paging.dataSource.TopRatedMoviesDataSource
import br.com.jeramovies.data.paging.factory.SearchMoviesDataSourceFactory
import br.com.jeramovies.domain.entity.Movie
import br.com.jeramovies.domain.repository.MoviesRepository
import br.com.jeramovies.presentation.ui.movie.detail.MovieDetailsNavData
import br.com.jeramovies.presentation.util.base.BaseViewModel

class MoviesViewModel(
    private val repository: MoviesRepository
) : BaseViewModel() {

    val popularMovies: LiveData<PagedList<Movie>>
    val topRatedMovies: LiveData<PagedList<Movie>>
    val nowPlayingMovies: LiveData<PagedList<Movie>>
    val searchMovies: LiveData<PagedList<Movie>>
    val loading: LiveData<Boolean> get() = _loading

    private val _loading by lazy { MutableLiveData<Boolean>() }

    private val config = PagedList.Config.Builder()
        .setEnablePlaceholders(true)
        .setPageSize(30)
        .build()

    private val dataSourceFactory =
        SearchMoviesDataSourceFactory(
            repository,
            viewModelScope,
            onLoading = { _loading.postValue(it) },
            onFailure = { showDialog(it) }
        )

    private val popularMoviesFactory = object : DataSource.Factory<Int, Movie>() {
        override fun create(): DataSource<Int, Movie> {
            return PopularMoviesDataSource(
                repository,
                viewModelScope,
                onLoading = { _loading.postValue(it) },
                onFailure = { showDialog(it) }
            )
        }
    }

    private val topRatedMoviesFactory = object : DataSource.Factory<Int, Movie>() {
        override fun create(): DataSource<Int, Movie> {
            return TopRatedMoviesDataSource(
                repository,
                viewModelScope,
                onLoading = { _loading.postValue(it) },
                onFailure = { showDialog(it) }
            )
        }
    }

    private val nowPlayingMoviesFactory = object : DataSource.Factory<Int, Movie>() {
        override fun create(): DataSource<Int, Movie> {
            return NowPlayingMoviesDataSource(
                repository,
                viewModelScope,
                onLoading = { _loading.postValue(it) },
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
        popularMovies = popularMoviesFactory.toLiveData(config)
        topRatedMovies = topRatedMoviesFactory.toLiveData(config)
        nowPlayingMovies = nowPlayingMoviesFactory.toLiveData(config)
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