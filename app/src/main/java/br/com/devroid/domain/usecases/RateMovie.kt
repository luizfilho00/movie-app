package br.com.devroid.domain.usecases

import br.com.devroid.data.dao.MovieDetailsDao
import br.com.devroid.data.dao.UserDao
import br.com.devroid.data.entity.local.DbUser
import br.com.devroid.data.remote.ApiService
import br.com.devroid.domain.entity.MovieRating

class RateMovie(
    private val apiService: ApiService,
    private val movieDetailsDao: MovieDetailsDao,
    private val userDao: UserDao
) {

    suspend fun execute(movieId: Int, rating: Float) {
        val user = userDao.getUser()
        return if (user != null) {
            if (user.token.isValid()) {
                sendRequest(movieId, rating)
            } else {
                generateNewToken()
                sendRequest(movieId, rating)
            }
        } else {
            generateNewToken()
            sendRequest(movieId, rating)
        }
    }

    private suspend fun generateNewToken() {
        val token = apiService.getToken()
        val user = userDao.getUser()
        if (user == null) userDao.insertUser(DbUser(token = token))
        else userDao.updateUser(DbUser(token = token))
    }

    private suspend fun sendRequest(movieId: Int, rating: Float) {
        val user = userDao.getUser()
        runCatching {
            apiService.rateMovie(movieId, MovieRating(rating), user?.token?.sessionId)
        }.onSuccess {
            updateMovies(movieId, rating)
        }
    }

    private suspend fun updateMovies(movieId: Int, rating: Float) {
        with(movieDetailsDao) {
            getMovie(movieId)?.let { movieWithGenre ->
                movieWithGenre.movie.rating = rating
                updateMovie(movieWithGenre.movie)
            }
        }
    }
}