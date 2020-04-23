package br.com.devroid.presentation.ui.movieDetails

import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.viewModelScope
import androidx.paging.DataSource
import androidx.paging.toLiveData
import br.com.devroid.data.paging.dataSource.RecommendationsMoviesDataSource
import br.com.devroid.domain.entity.*
import br.com.devroid.domain.repository.MoviesRepository
import br.com.devroid.domain.repository.MyListRepository
import br.com.devroid.domain.resource.StringResource
import br.com.devroid.domain.usecases.GetMovieShareText
import br.com.devroid.domain.usecases.RateMovie
import br.com.devroid.presentation.ui.trailer.TrailerNavData
import br.com.devroid.presentation.util.base.BaseViewModel
import br.com.devroid.presentation.util.livedata.SingleLiveEvent

class MovieDetailsViewModel(
    private val movieId: Int,
    private val strings: StringResource,
    private val rateMovie: RateMovie,
    private val myListRepository: MyListRepository,
    private val getMovieShareText: GetMovieShareText,
    private val repository: MoviesRepository
) : BaseViewModel() {

    val movieDetails: LiveData<MovieDetails> get() = _movieDetails
    val showThumbUpGreen: LiveData<Unit> get() = _showThumbUpGreen
    val showThumbUpRed: LiveData<Unit> get() = _showThumbUpRed
    val showRatingFragment: LiveData<Unit> get() = _showRatingFragment
    val updateSavedImage: LiveData<Boolean> get() = _updateSavedImage
    val openShareIntent: LiveData<String> get() = _openShareIntent
    val movieCrew: LiveData<List<Actor>> get() = Transformations.map(_movieCrew) { it.cast }
    val recommendedMovies by lazy { _recommendedMoviesFactory.toLiveData(config) }

    private val _movieDetails by lazy { MutableLiveData<MovieDetails>() }
    private val _showThumbUpGreen by lazy { SingleLiveEvent<Unit>() }
    private val _showThumbUpRed by lazy { SingleLiveEvent<Unit>() }
    private val _openShareIntent by lazy { SingleLiveEvent<String>() }
    private val _updateSavedImage by lazy { SingleLiveEvent<Boolean>() }
    private val _showRatingFragment by lazy { SingleLiveEvent<Unit>() }
    private val _movieCrew by lazy { MutableLiveData<MovieCast>() }
    private val _recommendedMoviesFactory = object : DataSource.Factory<Int, Movie>() {
        override fun create(): DataSource<Int, Movie> {
            return RecommendationsMoviesDataSource(
                repository,
                viewModelScope,
                movieId,
                onLoading = { _loading.value = it },
                onFailure = { showDialog(it) }
            )
        }
    }
    private var trailers: VideoResponse? = null

    init {
        launchAsync(
            block = { repository.getMovieDetails(movieId) },
            onSuccess = { movieDetails -> movieDetails?.let(_movieDetails::postValue) },
            onFailure = { error -> showDialog(error) }
        )
        launchAsync(
            block = { repository.getMovieCrew(movieId) },
            onSuccess = { crew -> _movieCrew.postValue(crew) },
            onFailure = { error -> showDialog(error) }
        )
        getTrailerPtBR { videos ->
            if (videos.results.isNullOrEmpty()) getTrailerInternational()
            else trailers = videos
        }
    }

    fun playTrailer() {
        trailers?.results?.firstOrNull()?.let { trailer ->
            goTo(TrailerNavData(trailer))
        } ?: showToast(strings.trailerNotFound)
    }

    fun onRecommendedClicked(movie: Movie) {
        goTo(MovieDetailsNavData(movie.id))
    }

    fun onRateMovieClicked() {
        _showRatingFragment.postValue(Unit)
    }

    fun onMovieRatingReceived(rating: Float) {
        launchAsync(
            block = { rateMovie.execute(movieId, rating) },
            onSuccess = {
                showThumbUpByRating(rating)
                showToast(strings.movieRatedSuccess)
            },
            onFailure = { showDialog(strings.movieRatedFailure) }
        )
    }

    fun onShareMovieClicked() {
        launchAsync(
            block = { getMovieShareText.execute(movieId) },
            onSuccess = { shareText -> _openShareIntent.postValue(shareText) },
            onFailure = { error -> showDialog(error) }
        )
    }

    fun onSaveMovieClicked() {
        launchAsync(
            block = { myListRepository.addOrRemoveFromList(movieId) },
            onSuccess = { saved ->
                _updateSavedImage.postValue(saved)
                if (saved) {
                    showToast(strings.movieSavedToList, Toast.LENGTH_SHORT)
                } else {
                    showToast(strings.movieRemovedFromList, Toast.LENGTH_SHORT)
                }
            },
            onFailure = { showDialog(it) }
        )
    }

    private fun showThumbUpByRating(rating: Float) {
        if (rating >= 5f) _showThumbUpGreen.postValue(Unit)
        else _showThumbUpRed.postValue(Unit)
    }

    private fun getTrailerPtBR(onSuccess: (VideoResponse) -> Unit) {
        launchAsync(
            block = { repository.getTrailers(movieId) },
            onSuccess = onSuccess,
            onFailure = { error -> showDialog(error) }
        )
    }

    private fun getTrailerInternational() {
        launchAsync(
            block = { repository.getTrailers(movieId, "") },
            onSuccess = { videos -> trailers = videos },
            onFailure = { error -> showDialog(error) }
        )
    }
}