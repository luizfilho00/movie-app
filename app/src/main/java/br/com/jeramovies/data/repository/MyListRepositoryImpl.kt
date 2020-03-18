package br.com.jeramovies.data.repository

import br.com.jeramovies.domain.entity.Movie
import br.com.jeramovies.domain.entity.MovieSaved
import br.com.jeramovies.domain.repository.MyListRepository

class MyListRepositoryImpl : MyListRepository {

    private var savedMovies = mutableSetOf<MovieSaved>()

    override fun getSavedMovies() = savedMovies.toList()

    override fun saveMovie(movie: Movie): Boolean {
        val movieToSave = MovieSaved(movie.id, movie.title, movie.voteAverage, movie.getPosterUrl())
        return if (savedMovies.add(movieToSave))
            true
        else savedMovies.remove(movieToSave)
    }
}