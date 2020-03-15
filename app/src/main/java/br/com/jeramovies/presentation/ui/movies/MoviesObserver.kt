package br.com.jeramovies.presentation.ui.movies

import androidx.lifecycle.LifecycleOwner
import androidx.paging.PagedList
import br.com.jeramovies.domain.entity.Movie
import br.com.jeramovies.presentation.util.livedata.observe

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
            is PopularMovies -> viewModel.popularMovies.observe(
                lifecycleOwner,
                moviesReceivedCallback
            )
            is InTheatersMovies -> viewModel.inTheatersMovies.observe(
                lifecycleOwner,
                moviesReceivedCallback
            )
            is TopRatedMovies -> viewModel.topRatedMovies.observe(
                lifecycleOwner,
                moviesReceivedCallback
            )
        }
    }
}