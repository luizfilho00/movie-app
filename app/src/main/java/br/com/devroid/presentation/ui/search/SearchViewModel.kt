package br.com.devroid.presentation.ui.search

import android.view.View
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.DataSource
import androidx.paging.PagedList
import androidx.paging.toLiveData
import br.com.devroid.data.paging.factory.SearchMoviesDataSourceFactory
import br.com.devroid.domain.entity.Movie
import br.com.devroid.domain.repository.MoviesRepository
import br.com.devroid.domain.repository.MyListRepository
import br.com.devroid.domain.resource.StringResource
import br.com.devroid.presentation.ui.movieDetails.MovieDetailsNavData
import br.com.devroid.presentation.util.base.BaseViewModel
import br.com.devroid.presentation.util.livedata.SingleLiveEvent

class SearchViewModel(
    repository: MoviesRepository,
    private val myListRepository: MyListRepository,
    private val strings: StringResource
) : BaseViewModel() {

    val searchMovies: LiveData<PagedList<Movie>> get() = _searchMovies
    val goToMovieDetails: LiveData<Pair<Movie, View>> get() = _goToMovieDetails

    private val _goToMovieDetails by lazy { SingleLiveEvent<Pair<Movie, View>>() }
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

    fun onMovieClicked(movie: Movie, view: View) {
        _goToMovieDetails.postValue(movie to view)
    }

    fun onSaveClicked(movie: Movie) {
        launchAsync(
            block = { myListRepository.addOrRemoveFromList(movie) },
            onSuccess = {
                if (movie.saved) {
                    showToast(strings.movieSavedToList, Toast.LENGTH_SHORT)
                } else {
                    showToast(strings.movieRemovedFromList, Toast.LENGTH_SHORT)
                }
            },
            onFailure = { showDialog(it) }
        )
    }

    private fun reloadSearch() {
        _searchMovies.value?.dataSource?.invalidate()
    }
}