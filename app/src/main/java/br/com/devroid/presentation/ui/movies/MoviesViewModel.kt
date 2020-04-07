package br.com.devroid.presentation.ui.movies

import android.widget.Toast
import androidx.lifecycle.viewModelScope
import androidx.paging.toLiveData
import br.com.devroid.data.paging.callback.MovieBoundaryCallback
import br.com.devroid.domain.entity.Movie
import br.com.devroid.domain.repository.InTheatersMoviesRepository
import br.com.devroid.domain.repository.MyListRepository
import br.com.devroid.domain.repository.PopularMoviesRepository
import br.com.devroid.domain.repository.TopRatedMoviesRepository
import br.com.devroid.domain.resource.StringResource
import br.com.devroid.presentation.ui.movieDetails.MovieDetailsNavData
import br.com.devroid.presentation.util.base.BaseViewModel

class MoviesViewModel(
    private val popularMoviesRepository: PopularMoviesRepository,
    private val inTheatersMoviesRepository: InTheatersMoviesRepository,
    private val topRatedMoviesRepository: TopRatedMoviesRepository,
    private val myListRepository: MyListRepository,
    private val strings: StringResource
) : BaseViewModel() {

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

    fun onMovieClick(movie: Movie) {
        goTo(MovieDetailsNavData(movie.id))
    }

    fun onSaveClicked(movie: Movie) {
        launchAsync(
            block = { myListRepository.addOrRemoveFromList(movie) },
            onSuccess = {
                if (movie.saved) {
                    showToast(strings.movieSavedToList, Toast.LENGTH_SHORT)
                } else {
                    showToast(strings.movieRemovedFromList, Toast.LENGTH_SHORT)
                }
            },
            onFailure = { showDialog(it) }
        )
    }
}