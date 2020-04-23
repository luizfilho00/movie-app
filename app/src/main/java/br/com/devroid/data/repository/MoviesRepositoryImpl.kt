package br.com.devroid.data.repository

import br.com.devroid.data.dao.GenreDao
import br.com.devroid.data.dao.MovieDetailsDao
import br.com.devroid.data.entity.local.MovieDetailsGenreCrossRef
import br.com.devroid.data.remote.ApiService
import br.com.devroid.domain.entity.MovieDetails
import br.com.devroid.domain.repository.MoviesRepository

class MoviesRepositoryImpl(
    private val apiService: ApiService,
    private val genreDao: GenreDao,
    private val movieDetailsDao: MovieDetailsDao
) : MoviesRepository {

    override suspend fun getMovieDetails(id: Int): MovieDetails? {
        val movieFromDb = getMovieWithGenresFromDb(id)
        return if (movieFromDb == null) {
            val movieFromApi = apiService.getMovieDetails(id).toMovieDetails()
            movieDetailsDao.insertMovie(movieFromApi)
            createRelationTable(movieFromApi)
            getMovieWithGenresFromDb(id)
        } else {
            movieFromDb
        }
    }

    override suspend fun searchMovies(text: String, page: Int) =
        apiService.searchMovies(text, page).toMovieResponse()

    override suspend fun getRecommendations(id: Int, page: Int) =
        apiService.getRecommendations(id, page).toMovieResponse()

    override suspend fun getMovieCrew(id: Int) = apiService.getMovieCrew(id).toMovieCast()

    override suspend fun getTrailers(id: Int, language: String) =
        apiService.getMovieTrailers(id, language).toVideoResponse()

    private suspend fun createRelationTable(movie: MovieDetails) {
        genreDao.insertAll(movie.genres)
        movie.genres.forEach { genre ->
            movieDetailsDao.insertMovieWithGenre(
                MovieDetailsGenreCrossRef(movie.id, genre.id)
            )
        }
    }

    private suspend fun getMovieWithGenresFromDb(id: Int): MovieDetails? {
        val movieWithGenres = movieDetailsDao.getMovie(id)
        return movieWithGenres?.movie?.apply { genres = movieWithGenres.genres }
    }
}