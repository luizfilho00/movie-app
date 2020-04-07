package br.com.devroid.presentation.ui.myList

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import br.com.devroid.domain.entity.MovieSaved
import br.com.devroid.domain.repository.MyListRepository
import br.com.devroid.presentation.ui.movieDetails.MovieDetailsNavData
import br.com.devroid.presentation.util.base.BaseViewModel
import br.com.devroid.presentation.util.livedata.SingleLiveEvent

class MyListViewModel(private val repository: MyListRepository) : BaseViewModel() {

    val savedMovies: LiveData<List<MovieSaved>> get() = _savedMovies
    val showRemoveAlert: LiveData<MovieSaved> get() = _showRemoveAlert

    private val _showRemoveAlert by lazy { SingleLiveEvent<MovieSaved>() }
    private val _savedMovies by lazy { MutableLiveData<List<MovieSaved>>() }

    init {
        loadMovies()
    }

    fun loadMovies() {
        launchAsync(
            block = { repository.getSavedMovies() },
            onSuccess = { savedMovies -> _savedMovies.value = savedMovies },
            onFailure = { showDialog(it) }
        )
    }

    fun onMovieClicked(movieSaved: MovieSaved) {
        goTo(MovieDetailsNavData(movieSaved.id))
    }

    fun onMovieLongClicked(movieSaved: MovieSaved) {
        _showRemoveAlert.value = movieSaved
    }
}