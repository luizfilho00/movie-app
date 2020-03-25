package br.com.jeramovies.data.entity.local

import br.com.jeramovies.domain.entity.Movie
import io.realm.RealmList
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

open class DbPopularMovie(
    @PrimaryKey override var sequenceId: Long = 0,
    override var id: Int = 0,
    override var popularity: Double = 0.0,
    override var voteCount: Int = 0,
    override var video: Boolean = false,
    override var posterPath: String? = "",
    override var adult: Boolean = false,
    override var backdropPath: String? = "",
    override var originalLanguage: String? = "",
    override var originalTitle: String? = "",
    override var genreIds: RealmList<Int> = RealmList(),
    override var title: String = "",
    override var voteAverage: Double = 0.0,
    override var overview: String? = "",
    override var releaseDate: String? = "",
    override var saved: Boolean
) : DbMovie, RealmObject() {

    constructor() : this(
        0,
        0,
        0.0,
        0,
        false,
        "",
        false,
        "",
        "",
        "",
        RealmList<Int>(),
        "",
        0.0,
        "",
        "",
        false
    )

    override fun fromMovie(movie: Movie) =
        DbPopularMovie(
            id = movie.id,
            popularity = movie.popularity,
            voteCount = movie.voteCount,
            video = movie.video,
            posterPath = movie.posterPath,
            adult = movie.adult,
            backdropPath = movie.backdropPath,
            originalLanguage = movie.originalLanguage,
            originalTitle = movie.originalTitle,
            genreIds = RealmList<Int>().apply { addAll(movie.genreIds) },
            title = movie.title,
            voteAverage = movie.voteAverage,
            overview = movie.overview,
            releaseDate = movie.releaseDate,
            saved = movie.saved,
            sequenceId = movie.sequenceId
        )

    companion object {
        fun fromMovie(movie: Movie) = DbPopularMovie().fromMovie(movie)
    }
}