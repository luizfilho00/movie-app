package br.com.jeramovies.data.repository

import android.util.Log
import br.com.jeramovies.data.entity.local.DbLastPageLoadedFromNetwork
import br.com.jeramovies.data.entity.local.DbMovie
import br.com.jeramovies.data.entity.local.DbPopularMovie
import br.com.jeramovies.data.remote.ApiService
import br.com.jeramovies.data.util.addAllToDatabase
import br.com.jeramovies.domain.entity.Movie
import br.com.jeramovies.domain.entity.MovieType
import br.com.jeramovies.domain.repository.PopularMoviesRepository
import io.realm.Realm

class PopularMoviesRepositoryImpl(
    private val apiService: ApiService,
    private val realm: Realm
) : PopularMoviesRepository {

    override suspend fun loadFromNetwork(page: Int) {
        val movies = getMoviesFromNetwork(page)
        var lastId = realm.where(DbPopularMovie::class.java)
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
        return realm.where(DbPopularMovie::class.java)
            .greaterThan(DbMovie.FIELD_SEQUENCE_ID, sequenceId)
            .findAll()
            .map { it.toMovie() }
    }

    override fun updateLastPageLoadedFromNetwork(page: Int) {
        try {
            updateLastPageOnDb(page)
        } catch (ex: Exception) {
            Log.d("PopularRepository", ex.toString())
        }
    }

    override fun getLastPageLoadedFromNetwork(): Int {
        return realm.where(DbLastPageLoadedFromNetwork::class.java)
            .findFirst()
            ?.lastPopularMoviePage
            ?: 1
    }

    private fun updateLastPageOnDb(page: Int) {
        realm.executeTransaction {
            val dbPage = realm.where(DbLastPageLoadedFromNetwork::class.java)
                .findFirst()
            dbPage?.let {
                dbPage.lastPopularMoviePage = page
            } ?: run {
                realm.copyToRealm(DbLastPageLoadedFromNetwork(lastPopularMoviePage = page))
            }
        }
    }

    private suspend fun getMoviesFromNetwork(page: Int): List<DbPopularMovie> {
        return apiService
            .getPopularMovies(page)
            .movies.map {
            DbPopularMovie.fromMovie(
                it.toMovie().apply { type = MovieType.PopularMovies }
            )
        }
    }
}