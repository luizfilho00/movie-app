package br.com.jeramovies.presentation.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.jeramovies.domain.entity.Movie
import br.com.jeramovies.domain.repository.MoviesRepository
import kotlinx.coroutines.launch

class MainViewModel(
    private val repository: MoviesRepository
) : ViewModel() {

    val movies: LiveData<List<Movie>> get() = _movies

    val _movies by lazy { MutableLiveData<List<Movie>>() }

    init {
        viewModelScope.launch {
            runCatching { repository.getMovies() }
                .onSuccess { movies ->
                    _movies.value = movies
                }.onFailure {
                    _movies.value = listOf()
                }
        }
    }
}