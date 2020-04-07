package br.com.devroid.data.repository

import androidx.paging.DataSource
import br.com.devroid.data.dao.PageLoadedDao
import br.com.devroid.data.dao.TopRatedMoviesDao
import br.com.devroid.data.entity.local.DbLastPageLoadedFromNetwork
import br.com.devroid.data.entity.local.DbMovie
import br.com.devroid.data.entity.local.DbTopRatedMovie
import br.com.devroid.data.remote.ApiService
import br.com.devroid.domain.entity.Movie
import br.com.devroid.domain.entity.MovieType
import br.com.devroid.domain.repository.TopRatedMoviesRepository

class TopRatedMoviesRepositoryImpl(
    private val apiService: ApiService,
    private val pageLoadedDao: PageLoadedDao,
    private val dao: TopRatedMoviesDao
) : TopRatedMoviesRepository {

    override suspend fun getLastInsertedId(): Int {
        return dao.getCount()
    }

    override suspend fun loadFromNetwork(page: Int) =
        apiService.getTopRatedMovies(page).toMovieResponse(MovieType.TopRatedMovies)

    override suspend fun insertAll(movies: List<DbMovie>) {
        dao.insertAll(movies.map { it as DbTopRatedMovie })
    }

    override suspend fun updateLastPageLoadedFromNetwork(page: Int) {
        val updatedPage =
            pageLoadedDao.getLastPageLoaded()?.apply { lastTopRatedMoviePage = page }
        updatedPage?.let {
            pageLoadedDao.updatePage(updatedPage)
        }
    }

    override suspend fun getLastPageLoadedFromNetwork(): Int {
        val lastPage = pageLoadedDao.getLastPageLoaded()
        return if (lastPage == null) {
            pageLoadedDao.insertPage(DbLastPageLoadedFromNetwork())
            pageLoadedDao.getLastPageLoaded()!!.lastTopRatedMoviePage
        } else lastPage.lastTopRatedMoviePage
    }

    override fun getAll(): DataSource.Factory<Int, Movie> {
        return dao.getAll().map { it.toMovie() }
    }
}