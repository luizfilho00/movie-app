package br.com.jeramovies.data.repository

import androidx.paging.DataSource
import br.com.jeramovies.data.dao.InTheatersMoviesDao
import br.com.jeramovies.data.dao.PageLoadedDao
import br.com.jeramovies.data.entity.local.DbInTheatersMovie
import br.com.jeramovies.data.entity.local.DbLastPageLoadedFromNetwork
import br.com.jeramovies.data.entity.local.DbMovie
import br.com.jeramovies.data.remote.ApiService
import br.com.jeramovies.domain.entity.Movie
import br.com.jeramovies.domain.entity.MovieType
import br.com.jeramovies.domain.repository.InTheatersMoviesRepository

class InTheatersMoviesRepositoryImpl(
    private val apiService: ApiService,
    private val pageLoadedDao: PageLoadedDao,
    private val dao: InTheatersMoviesDao
) : InTheatersMoviesRepository {

    override suspend fun getLastInsertedId(): Int {
        return dao.getCount()
    }

    override suspend fun loadFromNetwork(page: Int) =
        apiService.getInTheatersMovies(page).toMovieResponse(MovieType.InTheatersMovies)

    override suspend fun insertAll(movies: List<DbMovie>) {
        dao.insertAll(movies.map { it as DbInTheatersMovie })
    }

    override suspend fun updateLastPageLoadedFromNetwork(page: Int) {
        val updatedPage =
            pageLoadedDao.getLastPageLoaded()?.apply { lastInTheatersMoviePage = page }
        updatedPage?.let {
            pageLoadedDao.updatePage(updatedPage)
        }
    }

    override suspend fun getLastPageLoadedFromNetwork(): Int {
        val lastPage = pageLoadedDao.getLastPageLoaded()
        return if (lastPage == null) {
            pageLoadedDao.insertPage(DbLastPageLoadedFromNetwork())
            pageLoadedDao.getLastPageLoaded()!!.lastInTheatersMoviePage
        } else lastPage.lastInTheatersMoviePage
    }

    override fun getAll(): DataSource.Factory<Int, Movie> {
        return dao.getAll().map { it.toMovie() }
    }
}