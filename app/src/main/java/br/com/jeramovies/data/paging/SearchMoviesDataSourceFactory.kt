package br.com.jeramovies.data.paging

import androidx.paging.DataSource
import br.com.jeramovies.domain.entity.Movie
import br.com.jeramovies.domain.repository.MoviesRepository
import kotlinx.coroutines.CoroutineScope

class SearchMoviesDataSourceFactory(
    private val repository: MoviesRepository,
    private val scope: CoroutineScope
) : DataSource.Factory<Int, Movie>() {

    var text = ""

    override fun create(): DataSource<Int, Movie> {
        return SearchMoviesDataSource(text, repository, scope)
    }
}