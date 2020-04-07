package br.com.devroid.presentation.ui.movieDetails

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.viewModelScope
import androidx.paging.DataSource
import androidx.paging.toLiveData
import br.com.devroid.data.paging.dataSource.RecommendationsMoviesDataSource
import br.com.devroid.domain.entity.*
import br.com.devroid.domain.repository.MoviesRepository
import br.com.devroid.domain.resource.StringResource
import br.com.devroid.presentation.ui.trailer.TrailerNavData
import br.com.devroid.presentation.util.base.BaseViewModel

class MovieDetailsViewModel(
    private val movieId: Int,
    private val strings: StringResource,
    private val repository: MoviesRepository
) : BaseViewModel() {

    val movieDetails: LiveData<MovieDetails> get() = _movieDetails
    val movieCrew: LiveData<List<Actor>> get() = Transformations.map(_movieCrew) { it.cast }
    val recommendedMovies by lazy { _recommendedMoviesFactory.toLiveData(config) }

    private val _movieDetails by lazy { MutableLiveData<MovieDetails>() }
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
            onSuccess = { movieDetails -> _movieDetails.postValue(movieDetails) },
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