package br.com.devroid.data.repository

import br.com.devroid.data.dao.InTheatersMoviesDao
import br.com.devroid.data.dao.MoviesDao
import br.com.devroid.data.dao.PopularMoviesDao
import br.com.devroid.data.dao.TopRatedMoviesDao
import br.com.devroid.data.mappers.DbMovieSavedToMovieSaved
import br.com.devroid.data.mappers.MovieSavedToDbMovieSaved
import br.com.devroid.data.mappers.MovieToDbMovieSaved
import br.com.devroid.domain.entity.Movie
import br.com.devroid.domain.entity.MovieSaved
import br.com.devroid.domain.repository.MyListRepository

class MyListRepositoryImpl(
    private val moviesDao: MoviesDao,
    private val topRatedMoviesDao: TopRatedMoviesDao,
    private val inTheatersMoviesDao: InTheatersMoviesDao,
    private val popularMoviesDao: PopularMoviesDao
) : MyListRepository {

    override suspend fun getSavedMovies() =
        moviesDao.getSavedList().map(DbMovieSavedToMovieSaved()::transform)

    override suspend fun addOrRemoveFromList(movie: Movie) {
        updateSavedMovieStatus(movie.id, movie.saved)
        val savedMovie = moviesDao.findById(movie.id)
        if (savedMovie != null) {
            moviesDao.delete(savedMovie)
        } else {
            moviesDao.saveToMyList(MovieToDbMovieSaved().transform(movie))
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
    }
}