package br.com.jeramovies.data.repository

import android.util.Log
import br.com.jeramovies.data.entity.local.DbInTheatersMovie
import br.com.jeramovies.data.entity.local.DbLastPageLoadedFromNetwork
import br.com.jeramovies.data.entity.local.DbMovie
import br.com.jeramovies.data.remote.ApiService
import br.com.jeramovies.data.util.addAllToDatabase
import br.com.jeramovies.domain.entity.Movie
import br.com.jeramovies.domain.entity.MovieType
import br.com.jeramovies.domain.repository.InTheatersMoviesRepository
import io.realm.Realm

class InTheatersMoviesRepositoryImpl(
    private val apiService: ApiService,
    private val realm: Realm
) : InTheatersMoviesRepository {

    override suspend fun loadFromNetwork(page: Int) {
        val movies = getMoviesFromNetwork(page)
        var lastId = realm.where(DbInTheatersMovie::class.java)
            .max(DbMovie.FIELD_SEQUENCE_ID)
            ?.toLong()
        if (lastId == null) {
            realm.addAllToDatabase(
                movies.mapIndexed { index, movie ->
                    movie.apply { sequenceId = index.toLong() }
                }
            )
        } else {
            realm.addAllToDatabase(
                movies.map { movie -> movie.apply { sequenceId = ++lastId } }
            )
        }
    }

    override fun loadAfterSequenceIdFromDatabase(sequenceId: Long): List<Movie> {
        return realm.where(DbInTheatersMovie::class.java)
            .greaterThan(DbMovie.FIELD_SEQUENCE_ID, sequenceId)
            .findAll()
            .map { it.toMovie() }
    }

    override fun updateLastPageLoadedFromNetwork(page: Int) {
        try {
            updateLastPageOnDb(page)
        } catch (ex: Exception) {
            Log.d("InTheatersRepository", ex.toString())
        }
    }

    override fun getLastPageLoadedFromNetwork(): Int {
        return realm.where(DbLastPageLoadedFromNetwork::class.java)
            .findFirst()
            ?.lastInTheatersMoviePage
            ?: 1
    }

    private fun updateLastPageOnDb(page: Int) {
        realm.executeTransaction {
            val dbPage = realm.where(DbLastPageLoadedFromNetwork::class.java)
                .findFirst()
            dbPage?.let {
                dbPage.lastInTheatersMoviePage = page
            } ?: run {
                realm.copyToRealm(DbLastPageLoadedFromNetwork(lastInTheatersMoviePage = page))
            }
        }
    }

    private suspend fun getMoviesFromNetwork(page: Int): List<DbInTheatersMovie> {
        return apiService
            .getInTheatersMovies(page)
            .movies.map {
            DbInTheatersMovie.fromMovie(
                it.toMovie().apply { type = MovieType.InTheatersMovies }
            )
        }
    }
}