package br.com.devroid.domain.repository

import br.com.devroid.data.entity.local.DbMovie
import br.com.devroid.domain.entity.MoviesResponse

interface BaseMoviesRepository {

    suspend fun loadFromNetwork(page: Int): MoviesResponse
    suspend fun insertAll(movies: List<DbMovie>)
    suspend fun updateLastPageLoadedFromNetwork(page: Int)
    suspend fun getLastPageLoadedFromNetwork(): Int
    suspend fun getLastInsertedId(): Int
}