package br.com.jeramovies.presentation.ui.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.viewModelScope
import androidx.paging.DataSource
import androidx.paging.PagedList
import androidx.paging.toLiveData
import br.com.jeramovies.data.paging.factory.SearchMoviesDataSourceFactory
import br.com.jeramovies.domain.entity.Movie
import br.com.jeramovies.domain.repository.MoviesRepository
import br.com.jeramovies.presentation.util.base.BaseViewModel

class SearchViewModel(
    private val repository: MoviesRepository
) : BaseViewModel() {

    val searchMovies: LiveData<PagedList<Movie>> get() = _mediatorSearch

    private val _mediatorSearch by lazy { MediatorLiveData<PagedList<Movie>>() }
    private val _pagedSearch by lazy { searchMovieFactory.toLiveData(config) }

    private val dataSourceFactory =
        SearchMoviesDataSourceFactory(
            repository,
            viewModelScope,
            onLoading = { _loading.postValue(it) }
        )

    private val searchMovieFactory = object : DataSource.Factory<Int, Movie>() {
        override fun create(): DataSource<Int, Movie> {
            return dataSourceFactory.create()
        }
    }

    init {
        _mediatorSearch.addSource(
            _pagedSearch,
            object : Observer<PagedList<Movie>> {
                var first = true

                override fun onChanged(pagedMovies: PagedList<Movie>?) {
                    if (!first) {
                        _mediatorSearch.value = pagedMovies
                    } else {
                        first = false
                    }
                }
            })
    }

    fun searchMovies(text: String) {
        dataSourceFactory.text = text
        reloadSearch()
    }

    private fun reloadSearch() {
        _pagedSearch.value?.dataSource?.invalidate()
    }
}