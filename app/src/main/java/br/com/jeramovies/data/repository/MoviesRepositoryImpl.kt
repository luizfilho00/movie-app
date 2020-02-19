package br.com.jeramovies.data.repository

import androidx.lifecycle.LiveData
import androidx.paging.DataSource
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import br.com.jeramovies.data.paging.MoviesDataSource
import br.com.jeramovies.data.remote.ApiService
import br.com.jeramovies.domain.entity.Movie
import br.com.jeramovies.domain.repository.MoviesRepository
import kotlinx.coroutines.CoroutineScope

class MoviesRepositoryImpl(
    private val apiService: ApiService
) : MoviesRepository {

    override fun getMovies(scope: CoroutineScope): LiveData<PagedList<Movie>> {
        val dataSourceFactory = object : DataSource.Factory<Int, Movie>() {
            override fun create(): DataSource<Int, Movie> {
                return MoviesDataSource({
                    apiService.getMovies(1)
                }, scope)
            }
        }
        return LivePagedListBuilder<Int, Movie>(dataSourceFactory, pageConfig).build()
    }

    override fun searchMovies(
        text: String,
        scope: CoroutineScope
    ): LiveData<PagedList<Movie>> {
        val dataSourceFactory = object : DataSource.Factory<Int, Movie>() {
            override fun create(): DataSource<Int, Movie> {
                return MoviesDataSource({
                    apiService.searchMovies(text, 1)
                }, scope)
            }
        }
        return LivePagedListBuilder<Int, Movie>(dataSourceFactory, pageConfig).build()
    }

    companion object {
        private val pageConfig = PagedList.Config.Builder()
            .setPageSize(10)
            .setEnablePlaceholders(true)
            .build()
    }
}