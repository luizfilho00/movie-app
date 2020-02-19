package br.com.jeramovies.presentation.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.DataSource
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import br.com.jeramovies.data.paging.MoviesDataSource
import br.com.jeramovies.domain.entity.Movie
import br.com.jeramovies.domain.repository.MoviesRepository
import br.com.jeramovies.presentation.util.base.BaseViewModel

class MainViewModel(
    private val repository: MoviesRepository
) : BaseViewModel() {

    var movies: LiveData<PagedList<Movie>>

    private val _moviesSearch by lazy { MutableLiveData<List<Movie>>() }
    private var localText = ""

    init {
        val config = PagedList.Config.Builder()
            .setPageSize(10)
            .setEnablePlaceholders(true)
            .build()
        val dataSourceFactory = object : DataSource.Factory<Int, Movie>() {
            override fun create(): DataSource<Int, Movie> {
                return MoviesDataSource(repository, viewModelScope)
            }
        }
        movies = LivePagedListBuilder<Int, Movie>(dataSourceFactory, config).build()
    }

    fun searchMovies(text: String) {
        if (text.isNotEmpty()) {
            localText = text
            searchMovies()
        }
    }

    private fun searchMovies(page: Int = 1) {
        //TODO -> SearchMoviews with paging
    }
}