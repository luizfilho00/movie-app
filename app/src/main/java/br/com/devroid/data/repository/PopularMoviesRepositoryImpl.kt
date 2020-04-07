package br.com.devroid.data.repository

import androidx.paging.DataSource
import br.com.devroid.data.dao.PageLoadedDao
import br.com.devroid.data.dao.PopularMoviesDao
import br.com.devroid.data.entity.local.DbLastPageLoadedFromNetwork
import br.com.devroid.data.entity.local.DbMovie
import br.com.devroid.data.entity.local.DbPopularMovie
import br.com.devroid.data.remote.ApiService
import br.com.devroid.domain.entity.Movie
import br.com.devroid.domain.entity.MovieType
import br.com.devroid.domain.repository.PopularMoviesRepository

class PopularMoviesRepositoryImpl(
    private val apiService: ApiService,
    private val pageLoadedDao: PageLoadedDao,
    private val dao: PopularMoviesDao
) : PopularMoviesRepository {

    override suspend fun getLastInsertedId(): Int {
        return dao.getCount()
    }

    override suspend fun loadFromNetwork(page: Int) =
        apiService.getPopularMovies(page).toMovieResponse(MovieType.PopularMovies)

    override suspend fun insertAll(movies: List<DbMovie>) {
        dao.insertAll(movies.map { it as DbPopularMovie })
    }

    override suspend fun updateLastPageLoadedFromNetwork(page: Int) {
        val updatedPage = pageLoadedDao.getLastPageLoaded()?.apply { lastPopularMoviePage = page }
        updatedPage?.let {
            pageLoadedDao.updatePage(updatedPage)
        }
    }

    override suspend fun getLastPageLoadedFromNetwork(): Int {
        val lastPage = pageLoadedDao.getLastPageLoaded()
        return if (lastPage == null) {
            pageLoadedDao.insertPage(DbLastPageLoadedFromNetwork())
            pageLoadedDao.getLastPageLoaded()!!.lastPopularMoviePage
        } else lastPage.lastPopularMoviePage
    }

    override fun getAll(): DataSource.Factory<Int, Movie> {
        return dao.getAll().map { it.toMovie() }
    }
}