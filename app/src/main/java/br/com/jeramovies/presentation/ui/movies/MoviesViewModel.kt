package br.com.jeramovies.presentation.ui.movies

import androidx.lifecycle.viewModelScope
import androidx.paging.DataSource
import androidx.paging.toLiveData
import br.com.jeramovies.data.paging.dataSource.NowPlayingMoviesDataSource
import br.com.jeramovies.data.paging.dataSource.PopularMoviesDataSource
import br.com.jeramovies.data.paging.dataSource.TopRatedMoviesDataSource
import br.com.jeramovies.domain.entity.Movie
import br.com.jeramovies.domain.repository.MoviesRepository
import br.com.jeramovies.presentation.ui.movie.detail.MovieDetailsNavData
import br.com.jeramovies.presentation.util.base.BaseViewModel

class MoviesViewModel(
    private val repository: MoviesRepository
) : BaseViewModel() {

    val popularMovies by lazy { popularMoviesFactory.toLiveData(config) }
    val inTheatersMovies by lazy { inTheatersMoviesFactory.toLiveData(config) }
    val topRatedMovies by lazy { topRatedMoviesFactory.toLiveData(config) }

    private val popularMoviesFactory = object : DataSource.Factory<Int, Movie>() {
        override fun create(): DataSource<Int, Movie> {
            return PopularMoviesDataSource(
                repository,
                viewModelScope,
                onLoading = { _loading.value = it },
                onFailure = { showDialog(it) }
            )
        }
    }
    private val topRatedMoviesFactory = object : DataSource.Factory<Int, Movie>() {
        override fun create(): DataSource<Int, Movie> {
            return TopRatedMoviesDataSource(
                repository,
                viewModelScope,
                onLoading = { _loading.value = it },
                onFailure = { showDialog(it) }
            )
        }
    }

    private val inTheatersMoviesFactory = object : DataSource.Factory<Int, Movie>() {
        override fun create(): DataSource<Int, Movie> {
            return NowPlayingMoviesDataSource(
                repository,
                viewModelScope,
                onLoading = { _loading.value = it },
                onFailure = { showDialog(it) }
            )
        }
    }

    fun onMovieClick(movie: Movie) {
        goTo(MovieDetailsNavData(movie.id))
    }
}