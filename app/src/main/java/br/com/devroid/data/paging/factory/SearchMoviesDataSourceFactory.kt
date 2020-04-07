package br.com.devroid.data.paging.factory

import androidx.paging.DataSource
import br.com.devroid.data.paging.dataSource.SearchMoviesDataSource
import br.com.devroid.domain.entity.Movie
import br.com.devroid.domain.repository.MoviesRepository
import kotlinx.coroutines.CoroutineScope

class SearchMoviesDataSourceFactory(
    private val repository: MoviesRepository,
    private val scope: CoroutineScope,
    private val onLoading: ((Boolean) -> Unit)? = null,
    private val onFailure: ((Throwable) -> Unit)? = null
) : DataSource.Factory<Int, Movie>() {

    var text = ""

    override fun create(): DataSource<Int, Movie> {
        return SearchMoviesDataSource(
            text,
            repository,
            scope,
            onLoading,
            onFailure
        )
    }
}