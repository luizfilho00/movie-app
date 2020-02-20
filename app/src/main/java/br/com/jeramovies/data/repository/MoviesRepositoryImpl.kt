package br.com.jeramovies.data.repository

import androidx.paging.DataSource
import br.com.jeramovies.data.paging.MoviesDataSource
import br.com.jeramovies.data.remote.ApiService
import br.com.jeramovies.domain.entity.Movie
import br.com.jeramovies.domain.repository.MoviesRepository
import kotlinx.coroutines.CoroutineScope

class MoviesRepositoryImpl(
    private val apiService: ApiService
) : MoviesRepository {

    override fun getMovies(scope: CoroutineScope): DataSource.Factory<Int, Movie> {
        return object : DataSource.Factory<Int, Movie>() {
            override fun create(): DataSource<Int, Movie> {
                return MoviesDataSource({
                    apiService.getMovies(1)
                }, scope)
            }
        }
    }

    override fun searchMovies(
        text: String,
        scope: CoroutineScope
    ): DataSource.Factory<Int, Movie> {
        return object : DataSource.Factory<Int, Movie>() {
            override fun create(): DataSource<Int, Movie> {
                return MoviesDataSource({
                    apiService.searchMovies(text, 1)
                }, scope)
            }
        }
    }
}