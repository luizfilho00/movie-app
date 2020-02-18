package br.com.jeramovies.presentation.ui.main

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import br.com.jeramovies.domain.entity.Movie
import br.com.jeramovies.domain.repository.MoviesRepository
import br.com.jeramovies.presentation.util.base.BaseViewModel

class MainViewModel(
    private val repository: MoviesRepository
) : BaseViewModel() {

    val movies: LiveData<Pair<List<Movie>, Boolean>> get() = _movies
    val emptyList: LiveData<Boolean> by lazy {
        Transformations.map(_movies) { (list, _) -> list.isEmpty() }
    }

    private val _movies by lazy { MutableLiveData<Pair<List<Movie>, Boolean>>() }

    init {
        loadMovies()
    }

    fun loadMovies(page: Int? = null) {
        launchAsync(
            block = { repository.getMovies(page) },
            onSuccess = { list -> _movies.value = list to false },
            onFailure = { error ->
                Log.d("GetMovies", error)
                _movies.value = listOf<Movie>() to false
            }
        )
    }

    fun searchMovies(text: String) {
        launchAsync(
            block = { repository.searchMovies(text) },
            onSuccess = { list -> _movies.value = list to true },
            onFailure = { _movies.value = listOf<Movie>() to false }
        )
    }
}