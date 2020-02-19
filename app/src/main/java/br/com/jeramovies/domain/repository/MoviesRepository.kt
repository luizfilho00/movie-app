package br.com.jeramovies.domain.repository

import androidx.lifecycle.LiveData
import androidx.paging.PagedList
import br.com.jeramovies.domain.entity.Movie
import kotlinx.coroutines.CoroutineScope

interface MoviesRepository {

    fun getMovies(scope: CoroutineScope): LiveData<PagedList<Movie>>
    fun searchMovies(text: String, scope: CoroutineScope): LiveData<PagedList<Movie>>
}