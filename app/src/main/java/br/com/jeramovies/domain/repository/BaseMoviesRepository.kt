package br.com.jeramovies.domain.repository

import br.com.jeramovies.domain.entity.Movie

interface BaseMoviesRepository {

    suspend fun loadFromNetwork(page: Int)
    fun loadAfterSequenceIdFromDatabase(sequenceId: Long): List<Movie>
    fun updateLastPageLoadedFromNetwork(page: Int)
    fun getLastPageLoadedFromNetwork(): Int
}