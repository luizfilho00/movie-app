package br.com.jeramovies.domain.repository

import br.com.jeramovies.domain.entity.Movie
import br.com.jeramovies.domain.entity.MovieSaved

interface MyListRepository {

    suspend fun addOrRemoveFromList(movie: Movie)
    suspend fun remove(movie: MovieSaved): Boolean
    suspend fun getSavedMovies(): List<MovieSaved>
}