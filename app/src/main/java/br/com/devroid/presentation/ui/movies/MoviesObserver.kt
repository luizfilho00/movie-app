package br.com.devroid.presentation.ui.movies

import androidx.lifecycle.LifecycleOwner
import androidx.paging.PagedList
import br.com.devroid.domain.entity.Movie
import br.com.devroid.domain.entity.MovieType
import br.com.devroid.presentation.util.livedata.observe

interface MoviesObserver {
    fun observeMoviesResponse(
        lifecycleOwner: LifecycleOwner,
        movieType: MovieType?,
        viewModel: MoviesViewModel,
        moviesReceivedCallback: (PagedList<Movie>) -> Unit
    )
}

class MoviesObserverImpl : MoviesObserver {
    override fun observeMoviesResponse(
        lifecycleOwner: LifecycleOwner,
        movieType: MovieType?,
        viewModel: MoviesViewModel,
        moviesReceivedCallback: (PagedList<Movie>) -> Unit
    ) {
        when (movieType) {
            is MovieType.PopularMovies -> viewModel.popularMovies.observe(
                lifecycleOwner,
                moviesReceivedCallback
            )
            is MovieType.InTheatersMovies -> viewModel.inTheatersMovies.observe(
                lifecycleOwner,
                moviesReceivedCallback
            )
            is MovieType.TopRatedMovies -> viewModel.topRatedMovies.observe(
                lifecycleOwner,
                moviesReceivedCallback
            )
        }
    }
}