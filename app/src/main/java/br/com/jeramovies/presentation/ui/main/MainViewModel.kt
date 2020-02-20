package br.com.jeramovies.presentation.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.PagedList
import androidx.paging.toLiveData
import br.com.jeramovies.domain.entity.Movie
import br.com.jeramovies.domain.repository.MoviesRepository
import br.com.jeramovies.presentation.util.base.BaseViewModel

class MainViewModel(
    private val repository: MoviesRepository
) : BaseViewModel() {

    var movies: LiveData<PagedList<Movie>>

    init {
        val config = PagedList.Config.Builder()
            .setPageSize(10)
            .build()
        movies = repository.getMovies(viewModelScope).toLiveData(config)
    }

    fun searchMovies(text: String) {
        //TODO ->
    }
}