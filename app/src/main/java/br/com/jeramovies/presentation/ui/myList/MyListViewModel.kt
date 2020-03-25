package br.com.jeramovies.presentation.ui.myList

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import br.com.jeramovies.domain.entity.MovieSaved
import br.com.jeramovies.domain.repository.MyListRepository
import br.com.jeramovies.presentation.ui.movieDetails.MovieDetailsNavData
import br.com.jeramovies.presentation.util.base.BaseViewModel

class MyListViewModel(private val repository: MyListRepository) : BaseViewModel() {

    val savedMovies: LiveData<List<MovieSaved>> get() = _savedMovies

    private val _savedMovies by lazy { MutableLiveData<List<MovieSaved>>() }

    init {
        loadMovies()
    }

    fun loadMovies() {
        launchAsync(
            block = { repository.getSavedMovies() },
            onSuccess = { savedMovies -> _savedMovies.value = savedMovies }
        )
    }

    fun onMovieClicked(movieSaved: MovieSaved) {
        goTo(MovieDetailsNavData(movieSaved.id))
    }
}