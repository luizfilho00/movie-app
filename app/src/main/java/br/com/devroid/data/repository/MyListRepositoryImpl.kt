package br.com.devroid.data.repository

import br.com.devroid.data.dao.*
import br.com.devroid.data.mappers.DbMovieSavedToMovieSaved
import br.com.devroid.data.mappers.MovieDetailsToDbMovieSaved
import br.com.devroid.data.mappers.MovieSavedToDbMovieSaved
import br.com.devroid.domain.entity.MovieSaved
import br.com.devroid.domain.repository.MyListRepository

class MyListRepositoryImpl(
    private val moviesDao: MoviesDao,
    private val movieDetailsDao: MovieDetailsDao,
    private val topRatedMoviesDao: TopRatedMoviesDao,
    private val inTheatersMoviesDao: InTheatersMoviesDao,
    private val popularMoviesDao: PopularMoviesDao
) : MyListRepository {

    override suspend fun getSavedMovies() =
        moviesDao.getSavedList().map(DbMovieSavedToMovieSaved()::transform)

    override suspend fun addOrRemoveFromList(movieDetailsId: Int): Boolean {
        val movieSaved = moviesDao.findById(movieDetailsId)
        return if (movieSaved != null) {
            moviesDao.delete(movieSaved)
            updateSavedMovieStatus(movieDetailsId, false)
            false
        } else {
            val movieDetails = movieDetailsDao.getMovie(movieDetailsId)?.movie
            val dbMovieSaved = movieDetails?.let { MovieDetailsToDbMovieSaved().transform(it) }
            dbMovieSaved?.let { moviesDao.saveToMyList(dbMovieSaved) }
            updateSavedMovieStatus(movieDetailsId, true)
            true
        }
    }

    override suspend fun remove(movie: MovieSaved): Boolean {
        updateSavedMovieStatus(movie.id, false)
        return moviesDao.delete(MovieSavedToDbMovieSaved().transform(movie)) > 0
    }

    private suspend fun updateSavedMovieStatus(id: Int, status: Boolean) {
        topRatedMoviesDao.updateMovieSavedStatus(id, status)
        popularMoviesDao.updateMovieSavedStatus(id, status)
        inTheatersMoviesDao.updateMovieSavedStatus(id, status)
        movieDetailsDao.updateMovieSavedStatus(id, status)
    }
}