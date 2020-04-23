package br.com.devroid.presentation.ui.movies

import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.toLiveData
import br.com.devroid.data.paging.callback.MovieBoundaryCallback
import br.com.devroid.domain.entity.Movie
import br.com.devroid.domain.repository.InTheatersMoviesRepository
import br.com.devroid.domain.repository.PopularMoviesRepository
import br.com.devroid.domain.repository.TopRatedMoviesRepository
import br.com.devroid.presentation.util.base.BaseViewModel
import br.com.devroid.presentation.util.livedata.SingleLiveEvent

class MoviesViewModel(
    private val popularMoviesRepository: PopularMoviesRepository,
    private val inTheatersMoviesRepository: InTheatersMoviesRepository,
    private val topRatedMoviesRepository: TopRatedMoviesRepository
) : BaseViewModel() {

    val goToMovieDetails: LiveData<Pair<Movie, View>> get() = _goToMovieDetails
    val popularMovies by lazy {
        popularMoviesRepository.getAll()
            .toLiveData(
                config = config,
                boundaryCallback = MovieBoundaryCallback(
                    popularMoviesRepository,
                    viewModelScope,
                    onLoading = { _loading.value = it },
                    onFailure = { showDialog(it) }
                )
            )
    }
    val inTheatersMovies by lazy {
        inTheatersMoviesRepository.getAll()
            .toLiveData(
                config = config,
                boundaryCallback = MovieBoundaryCallback(
                    inTheatersMoviesRepository,
                    viewModelScope,
                    onLoading = { _loading.value = it },
                    onFailure = { showDialog(it) }
                )
            )
    }
    val topRatedMovies by lazy {
        topRatedMoviesRepository.getAll()
            .toLiveData(
                config = config,
                boundaryCallback = MovieBoundaryCallback(
                    topRatedMoviesRepository,
                    viewModelScope,
                    onLoading = { _loading.value = it },
                    onFailure = { showDialog(it) }
                )
            )
    }

    private val _goToMovieDetails by lazy { SingleLiveEvent<Pair<Movie, View>>() }

    fun onMovieClick(movie: Movie, view: View) {
        _goToMovieDetails.postValue(movie to view)
    }
}