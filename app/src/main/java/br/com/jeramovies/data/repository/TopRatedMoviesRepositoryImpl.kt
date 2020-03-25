package br.com.jeramovies.data.repository

import android.util.Log
import br.com.jeramovies.data.entity.local.DbLastPageLoadedFromNetwork
import br.com.jeramovies.data.entity.local.DbMovie
import br.com.jeramovies.data.entity.local.DbTopRatedMovie
import br.com.jeramovies.data.remote.ApiService
import br.com.jeramovies.data.util.addAllToDatabase
import br.com.jeramovies.domain.entity.Movie
import br.com.jeramovies.domain.entity.MovieType
import br.com.jeramovies.domain.repository.TopRatedMoviesRepository
import io.realm.Realm

class TopRatedMoviesRepositoryImpl(
    private val apiService: ApiService,
    private val realm: Realm
) : TopRatedMoviesRepository {

    override suspend fun loadFromNetwork(page: Int) {
        val movies = getMoviesFromNetwork(page)
        var lastId = realm.where(DbTopRatedMovie::class.java)
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
        return realm.where(DbTopRatedMovie::class.java)
            .greaterThan(DbMovie.FIELD_SEQUENCE_ID, sequenceId)
            .findAll()
            .map { it.toMovie() }
    }

    override fun updateLastPageLoadedFromNetwork(page: Int) {
        try {
            updateLastPageOnDb(page)
        } catch (ex: Exception) {
            Log.d("TopRatedRepository", ex.toString())
        }
    }

    override fun getLastPageLoadedFromNetwork(): Int {
        return realm.where(DbLastPageLoadedFromNetwork::class.java)
            .findFirst()
            ?.lastTopRatedMoviePage
            ?: 1
    }

    private fun updateLastPageOnDb(page: Int) {
        realm.executeTransaction {
            val dbPage = realm.where(DbLastPageLoadedFromNetwork::class.java)
                .findFirst()
            dbPage?.let {
                dbPage.lastTopRatedMoviePage = page
            } ?: run {
                realm.copyToRealm(DbLastPageLoadedFromNetwork(lastTopRatedMoviePage = page))
            }
        }
    }

    private suspend fun getMoviesFromNetwork(page: Int): List<DbTopRatedMovie> {
        return apiService
            .getTopRatedMovies(page)
            .movies.map {
            DbTopRatedMovie.fromMovie(
                it.toMovie().apply { type = MovieType.TopRatedMovies }
            )
        }
    }
}