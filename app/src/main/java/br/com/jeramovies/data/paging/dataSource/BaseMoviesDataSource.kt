package br.com.jeramovies.data.paging.dataSource

import android.util.Log
import androidx.paging.PageKeyedDataSource
import br.com.jeramovies.domain.entity.Movie
import br.com.jeramovies.domain.repository.BaseMoviesRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

abstract class BaseMoviesDataSource : PageKeyedDataSource<Int, Movie>() {

    abstract val repository: BaseMoviesRepository
    abstract val scope: CoroutineScope
    abstract val onLoading: ((Boolean) -> Unit)?
    abstract val onFailure: ((Throwable) -> Unit)?

    private var lastSequenceIdLoaded: Long = -1

    override fun loadInitial(
        params: LoadInitialParams<Int>,
        callback: LoadInitialCallback<Int, Movie>
    ) {
        scope.launch {
            val lastPageLoadedFromNetwork = repository.getLastPageLoadedFromNetwork()
            runCatching {
                onLoading?.invoke(true)
                loadMoreFromDatabase(lastSequenceIdLoaded)
            }.onSuccess { moviesFromDb ->
                Log.d("RealmDataSource", "PopularMovies -> loadInitial()\n${moviesFromDb}")
                onLoading?.invoke(false)
                callback.onResult(
                    if (moviesFromDb.isEmpty()) loadMoreFromNetwork(page = lastPageLoadedFromNetwork) else moviesFromDb,
                    null,
                    lastPageLoadedFromNetwork + 1
                )
            }.onFailure {
                onLoading?.invoke(false)
                onFailure?.invoke(it)
            }
        }
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, Movie>) {
        scope.launch {
            runCatching {
                loadMoreFromDatabase(lastSequenceIdLoaded)
            }.onSuccess { moviesFromDb ->
                callback.onResult(
                    if (moviesFromDb.isEmpty()) loadMoreFromNetwork(page = params.key) else moviesFromDb,
                    if (params.key > 1) params.key - 1 else null
                )
            }.onFailure {
                onFailure?.invoke(it)
            }
        }
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, Movie>) {
        scope.launch {
            runCatching {
                loadMoreFromDatabase(lastSequenceIdLoaded)
            }.onSuccess { moviesFromDb ->
                callback.onResult(
                    if (moviesFromDb.isEmpty()) loadMoreFromNetwork(page = params.key) else moviesFromDb,
                    if (params.key > 1) params.key + 1 else null
                )
            }.onFailure {
                onFailure?.invoke(it)
            }
        }
    }

    private suspend fun loadMoreFromNetwork(page: Int): List<Movie> {
        try {
            runCatching {
                repository.loadFromNetwork(page)
            }.onSuccess {
                repository.updateLastPageLoadedFromNetwork(page)
            }.onFailure {
                throw Exception()
            }
        } catch (ex: Exception) {
            /* Nothing to do */
        }
        return repository.loadAfterSequenceIdFromDatabase(lastSequenceIdLoaded).let { movies ->
            movies.lastOrNull()?.sequenceId?.let { lastSequenceIdLoaded = it }
            movies
        }
    }

    private fun loadMoreFromDatabase(sequenceId: Long): List<Movie> {
        return repository.loadAfterSequenceIdFromDatabase(sequenceId).let { movies ->
            movies.lastOrNull()?.sequenceId?.let { lastSequenceIdLoaded = it }
            movies
        }
    }
}