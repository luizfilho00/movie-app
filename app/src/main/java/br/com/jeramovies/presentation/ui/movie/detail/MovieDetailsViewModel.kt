package br.com.jeramovies.presentation.ui.movie.detail

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import br.com.jeramovies.R
import br.com.jeramovies.domain.entity.Actor
import br.com.jeramovies.domain.entity.MovieCast
import br.com.jeramovies.domain.entity.MovieDetails
import br.com.jeramovies.domain.repository.MoviesRepository
import br.com.jeramovies.presentation.ui.trailer.TrailerNavData
import br.com.jeramovies.presentation.util.base.BaseViewModel

class MovieDetailsViewModel(
    private val movieId: Int,
    private val appContext: Context,
    private val repository: MoviesRepository
) : BaseViewModel() {

    val movieDetails: LiveData<MovieDetails> get() = _movieDetails
    val movieCrew: LiveData<List<Actor>> get() = Transformations.map(_movieCrew) { it.cast }

    private val _movieDetails by lazy { MutableLiveData<MovieDetails>() }
    private val _movieCrew by lazy { MutableLiveData<MovieCast>() }

    init {
        launchAsync(
            block = { repository.getMovieDetails(movieId) },
            onSuccess = { movieDetails -> _movieDetails.postValue(movieDetails) },
            onFailure = { error -> showDialog(error) }
        )
        launchAsync(
            block = { repository.getMovieCrew(movieId) },
            onSuccess = { crew -> _movieCrew.postValue(crew) },
            onFailure = { error -> showDialog(error) }
        )
    }

    fun playTrailer() {
        launchAsync(
            block = { repository.getTrailers(movieId) },
            onSuccess = { trailers ->
                trailers.results?.firstOrNull()?.let { trailer ->
                    goTo(TrailerNavData(trailer))
                } ?: showToast(appContext.getString(R.string.trailer_not_found))
            },
            onFailure = { error -> showDialog(error) }
        )
    }
}