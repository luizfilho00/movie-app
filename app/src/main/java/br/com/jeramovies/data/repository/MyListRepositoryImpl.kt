package br.com.jeramovies.data.repository

import br.com.jeramovies.data.dao.InTheatersMoviesDao
import br.com.jeramovies.data.dao.MoviesDao
import br.com.jeramovies.data.dao.PopularMoviesDao
import br.com.jeramovies.data.dao.TopRatedMoviesDao
import br.com.jeramovies.data.mappers.DbMovieSavedToMovieSaved
import br.com.jeramovies.data.mappers.MovieSavedToDbMovieSaved
import br.com.jeramovies.data.mappers.MovieToDbMovieSaved
import br.com.jeramovies.domain.entity.Movie
import br.com.jeramovies.domain.entity.MovieSaved
import br.com.jeramovies.domain.repository.MyListRepository

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