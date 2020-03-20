package br.com.jeramovies.presentation.ui.search

import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.DataSource
import androidx.paging.PagedList
import androidx.paging.toLiveData
import br.com.jeramovies.data.paging.factory.SearchMoviesDataSourceFactory
import br.com.jeramovies.domain.entity.Movie
import br.com.jeramovies.domain.repository.MoviesRepository
import br.com.jeramovies.domain.repository.MyListRepository
import br.com.jeramovies.domain.resource.StringResource
import br.com.jeramovies.presentation.ui.movieDetails.MovieDetailsNavData
import br.com.jeramovies.presentation.util.base.BaseViewModel

class SearchViewModel(
    repository: MoviesRepository,
    private val myListRepository: MyListRepository,
    private val strings: StringResource
) : BaseViewModel() {

    val searchMovies: LiveData<PagedList<Movie>> get() = _searchMovies

    private val _searchMovies by lazy { searchMovieFactory.toLiveData(config) }
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

    fun searchMovies(text: String) {
        if (!text.isBlank()) {
            dataSourceFactory.text = text
            reloadSearch()
        }
    }

    fun onMovieClicked(movie: Movie) {
        goTo(MovieDetailsNavData(movie.id))
    }

    fun onSaveClicked(movie: Movie) {
        if (myListRepository.saveMovie(movie))
            showToast(strings.movieSavedToList, Toast.LENGTH_SHORT)
    }

    private fun reloadSearch() {
        _searchMovies.value?.dataSource?.invalidate()
    }
}