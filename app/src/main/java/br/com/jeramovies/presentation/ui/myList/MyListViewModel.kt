package br.com.jeramovies.presentation.ui.myList

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import br.com.jeramovies.domain.entity.MovieSaved
import br.com.jeramovies.domain.repository.MyListRepository
import br.com.jeramovies.presentation.ui.movie.detail.MovieDetailsNavData
import br.com.jeramovies.presentation.util.base.BaseViewModel

class MyListViewModel(repository: MyListRepository) : BaseViewModel() {

    val savedMovies: LiveData<List<MovieSaved>> get() = _savedMovies

    private val _savedMovies by lazy { MutableLiveData<List<MovieSaved>>() }

    init {
        _savedMovies.value = repository.getSavedMovies()
    }

    fun onMovieClicked(movieSaved: MovieSaved) {
        goTo(MovieDetailsNavData(movieSaved.id))
    }
}