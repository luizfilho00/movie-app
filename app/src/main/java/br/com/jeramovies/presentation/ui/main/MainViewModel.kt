package br.com.jeramovies.presentation.ui.main

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import br.com.jeramovies.domain.entity.Movie
import br.com.jeramovies.domain.repository.MoviesRepository
import br.com.jeramovies.presentation.util.base.BaseViewModel

class MainViewModel(
    private val repository: MoviesRepository
) : BaseViewModel() {

    val movies: LiveData<List<Movie>> get() = _movies
    val moviesSearch: LiveData<List<Movie>> get() = _moviesSearch

    private val _movies by lazy { MutableLiveData<List<Movie>>() }
    private val _moviesSearch by lazy { MutableLiveData<List<Movie>>() }
    private var localText = ""

    init {
        loadMovies()
    }

    fun loadMovies(page: Int? = null) {
        launchAsync(
            block = { repository.getMovies(page) },
            onSuccess = { list -> _movies.value = list },
            onFailure = { error ->
                Log.d("GetMovies", error)
                _movies.value = listOf()
            }
        )
    }

    fun searchMovies(text: String) {
        if (text.isNotEmpty()) {
            localText = text
            searchMovies()
        }
    }

    private fun searchMovies(page: Int = 1) {
        launchAsync(
            block = { repository.searchMovies(localText, page) },
            onSuccess = { list -> _moviesSearch.value = list }
        )
    }
}