package br.com.jeramovies.data.entity.local

import br.com.jeramovies.domain.entity.Movie
import io.realm.RealmList

interface DbMovie {

    var sequenceId: Long
    var id: Int
    var popularity: Double
    var voteCount: Int
    var video: Boolean
    var posterPath: String?
    var adult: Boolean
    var backdropPath: String?
    var originalLanguage: String?
    var originalTitle: String?
    var genreIds: RealmList<Int>
    var title: String
    var voteAverage: Double
    var overview: String?
    var releaseDate: String?
    var saved: Boolean

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
        genreIds = RealmList<Int>().apply { addAll(genreIds) },
        title = title,
        voteAverage = voteAverage,
        overview = overview ?: "",
        saved = saved,
        sequenceId = sequenceId,
        releaseDate = releaseDate
    )

    companion object {
        const val FIELD_ID = "id"
        const val FIELD_SEQUENCE_ID = "sequenceId"
    }
}