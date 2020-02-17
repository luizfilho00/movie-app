package br.com.jeramovies.presentation.ui.main

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.jeramovies.domain.entity.Movie
import br.com.jeramovies.domain.repository.MoviesRepository
import br.com.jeramovies.presentation.util.exceptionHandler.ExceptionHandler
import kotlinx.coroutines.launch

class MainViewModel(
    private val repository: MoviesRepository,
    private val exceptionHandler: ExceptionHandler
) : ViewModel() {

    val movies: LiveData<List<Movie>> get() = _movies

    private val _movies by lazy { MutableLiveData<List<Movie>>() }

    init {
        viewModelScope.launch {
            runCatching { repository.getMovies() }
                .onSuccess { movies ->
                    _movies.value = movies
                }.onFailure {
                    Log.d("GetMovies", exceptionHandler.resolveExceptionMessage(it))
                    _movies.value = listOf()
                }
        }
    }
}