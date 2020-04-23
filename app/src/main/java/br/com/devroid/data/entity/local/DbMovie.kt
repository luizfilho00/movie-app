package br.com.devroid.data.entity.local

import br.com.devroid.domain.entity.Movie

interface DbMovie {

    val id: Int
    val sequenceId: Int
    val popularity: Double
    val voteCount: Int
    val video: Boolean
    val posterPath: String?
    val adult: Boolean
    val backdropPath: String?
    val originalLanguage: String?
    val originalTitle: String?
    val genreIds: List<Int>
    val title: String
    val voteAverage: Double
    val overview: String?
    val releaseDate: String?
    val movieType: Int
    val saved: Boolean
    val rateByUser: Float?

    fun fromMovie(movie: Movie): DbMovie

    fun toMovie() = Movie(
        id = id,
        popularity = popularity,
        voteCount = voteCount,
        video = video,
        posterPath = posterPath ?: "",
        adult = adult,
        backdropPath = backdropPath ?: "",
        originalLanguage = originalLanguage ?: "",
        originalTitle = originalTitle ?: "",
        genreIds = genreIds,
        title = title,
        voteAverage = voteAverage,
        overview = overview ?: "",
        saved = saved,
        rateByUser = rateByUser,
        sequenceId = sequenceId,
        releaseDate = releaseDate
    )
}