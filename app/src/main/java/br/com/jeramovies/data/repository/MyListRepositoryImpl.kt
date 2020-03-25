package br.com.jeramovies.data.repository

import br.com.jeramovies.data.entity.local.DbInTheatersMovie
import br.com.jeramovies.data.entity.local.DbMovieSaved
import br.com.jeramovies.data.entity.local.DbPopularMovie
import br.com.jeramovies.data.entity.local.DbTopRatedMovie
import br.com.jeramovies.data.util.addOrRemoveIfExists
import br.com.jeramovies.domain.entity.Movie
import br.com.jeramovies.domain.entity.MovieManagedStatus
import br.com.jeramovies.domain.entity.MovieType
import br.com.jeramovies.domain.repository.MyListRepository
import io.realm.Realm

class MyListRepositoryImpl(
    private val realm: Realm
) : MyListRepository {

    override fun getSavedMovies() =
        realm.where(DbMovieSaved::class.java).findAll().map { it.toMovieSaved() }

    override fun updateSavedMovieStatus(movie: Movie): MovieManagedStatus {
        updateSavedFlagForMovie(movie)
        return realm.addOrRemoveIfExists(
            DbMovieSaved.fromMovie(movie),
            DbMovieSaved.FIELD_ID, movie.id
        )
    }

    private fun updateSavedFlagForMovie(movie: Movie) {
        realm.executeTransaction {
            when (movie.type) {
                is MovieType.PopularMovies ->
                    realm.copyToRealmOrUpdate(
                        DbPopularMovie.fromMovie(movie.apply { saved = !saved })
                    )
                is MovieType.TopRatedMovies ->
                    realm.copyToRealmOrUpdate(
                        DbTopRatedMovie.fromMovie(movie.apply { saved = !saved })
                    )
                is MovieType.InTheatersMovies ->
                    realm.copyToRealmOrUpdate(
                        DbInTheatersMovie.fromMovie(movie.apply { saved = !saved })
                    )
            }
        }
    }
}