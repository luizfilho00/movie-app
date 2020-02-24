package br.com.jeramovies.presentation.ui.movie.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import br.com.jeramovies.domain.entity.MovieDetails
import br.com.jeramovies.domain.repository.MoviesRepository
import br.com.jeramovies.presentation.util.base.BaseViewModel

class MovieDetailsViewModel(
    private val movieId: Int,
    private val repository: MoviesRepository
) : BaseViewModel() {

    val moveDetails: LiveData<MovieDetails> get() = _movieDetails

    private val _movieDetails by lazy { MutableLiveData<MovieDetails>() }

    init {
        launchAsync(
            block = { repository.getMovieDetails(movieId) },
            onSuccess = { movieDetails -> _movieDetails.postValue(movieDetails) },
            onFailure = { error -> showDialog(error) }
        )
    }
}