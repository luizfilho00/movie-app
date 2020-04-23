package br.com.devroid.domain.repository

import br.com.devroid.domain.entity.MovieSaved

interface MyListRepository {

    suspend fun addOrRemoveFromList(movieDetailsId: Int): Boolean
    suspend fun remove(movie: MovieSaved): Boolean
    suspend fun getSavedMovies(): List<MovieSaved>
}