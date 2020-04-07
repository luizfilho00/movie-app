package br.com.devroid.data.paging.callback

import androidx.paging.PagedList
import br.com.devroid.data.entity.local.DbMovie
import br.com.devroid.data.mappers.MovieToDbPopularMovie
import br.com.devroid.data.mappers.MovieToDbInTheatersMovie
import br.com.devroid.data.mappers.MovieToDbTopRatedMovie
import br.com.devroid.domain.entity.Movie
import br.com.devroid.domain.entity.MovieType
import br.com.devroid.domain.repository.BaseMoviesRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

class MovieBoundaryCallback(
    private val repository: BaseMoviesRepository,
    private val scope: CoroutineScope,
    private val onLoading: (Boolean) -> Unit,
    private val onFailure: (Throwable) -> Unit
) : PagedList.BoundaryCallback<Movie>() {

    private var loading = false

    private fun load() {
        if (loading) return
        loading = true
        scope.launch {
            try {
                val response = repository.loadFromNetwork(lastPage() + 1)
                repository.updateLastPageLoadedFromNetwork(response.page)
                var sequenceId = lastInsertedId() + 1
                repository.insertAll(response.movies.map {
                    sequenceId += 1
                    movieToDb(it, sequenceId)
                })
                loading = false
                onLoading(false)
            } catch (ex: Exception) {
                onFailure(ex)
                onLoading(false)
            }
        }
    }

    override fun onZeroItemsLoaded() {
        super.onZeroItemsLoaded()
        onLoading(true)
        load()
    }

    override fun onItemAtEndLoaded(itemAtEnd: Movie) {
        super.onItemAtEndLoaded(itemAtEnd)
        load()
    }

    private suspend fun lastPage() = repository.getLastPageLoadedFromNetwork()

    private suspend fun lastInsertedId() = repository.getLastInsertedId()

    private fun movieToDb(movie: Movie, sequenceId: Int): DbMovie {
        return when (movie.type) {
            MovieType.PopularMovies -> MovieToDbPopularMovie(sequenceId).transform(movie)
            MovieType.TopRatedMovies -> MovieToDbTopRatedMovie(sequenceId).transform(movie)
            MovieType.InTheatersMovies -> MovieToDbInTheatersMovie(sequenceId).transform(movie)
            else -> throw ClassNotFoundException("That movie type doesnt exists")
        }
    }
}