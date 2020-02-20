package br.com.jeramovies.domain.repository

import androidx.paging.DataSource
import br.com.jeramovies.domain.entity.Movie
import kotlinx.coroutines.CoroutineScope

interface MoviesRepository {

    fun getMovies(scope: CoroutineScope): DataSource.Factory<Int, Movie>
    fun searchMovies(text: String, scope: CoroutineScope): DataSource.Factory<Int, Movie>
}