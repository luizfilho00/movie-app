package br.com.devroid.domain.usecases

import br.com.devroid.data.dao.MovieDetailsDao
import br.com.devroid.data.remote.ApiService
import br.com.devroid.domain.util.PT_BR

class GetMovieShareText(
    private val dao: MovieDetailsDao,
    private val apiService: ApiService
) {

    suspend fun execute(id: Int): String {
        val movie = dao.getMovie(id)?.movie
        var trailer = apiService.getMovieTrailers(id, PT_BR).results
        if (trailer.isNullOrEmpty())
            trailer = apiService.getMovieTrailers(id, "").results
        return "Trailer: https://www.youtube.com/watch?v=${trailer?.firstOrNull()?.toTrailer()?.key}" +
                "\n\n" +
                "Página no IMDB: https://www.imdb.com/title/${movie?.imdbId}/"
    }
}