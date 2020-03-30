package br.com.jeramovies.domain.repository

import br.com.jeramovies.data.entity.local.DbMovie
import br.com.jeramovies.domain.entity.MoviesResponse

interface BaseMoviesRepository {

    suspend fun loadFromNetwork(page: Int): MoviesResponse
    suspend fun insertAll(movies: List<DbMovie>)
    suspend fun updateLastPageLoadedFromNetwork(page: Int)
    suspend fun getLastPageLoadedFromNetwork(): Int
    suspend fun getLastInsertedId(): Int
}