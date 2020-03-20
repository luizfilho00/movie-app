package br.com.jeramovies.data.repository

import br.com.jeramovies.data.util.addOrRemoveIfExists
import br.com.jeramovies.domain.entity.Movie
import br.com.jeramovies.domain.entity.MovieManagedStatus
import br.com.jeramovies.domain.entity.MovieSaved
import br.com.jeramovies.domain.repository.MyListRepository
import br.com.jeramovies.domain.util.W500
import io.realm.Realm

class MyListRepositoryImpl : MyListRepository {
    private val realm = Realm.getDefaultInstance()

    override fun getSavedMovies() = realm.where(MovieSaved::class.java).findAll().toList()

    override fun saveMovie(movie: Movie): MovieManagedStatus {
        val movieToSave = MovieSaved(
            movie.id,
            movie.title,
            movie.voteAverage,
            movie.getPosterUrl(W500)
        )
        return realm.addOrRemoveIfExists(movieToSave, "id", movieToSave.id)
    }
}