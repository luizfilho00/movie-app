package br.com.jeramovies.data.entity.local

import br.com.jeramovies.domain.entity.Movie
import io.realm.RealmList
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

open class DbInTheatersMovie(
    @PrimaryKey override var sequenceId: Long,
    override var id: Int,
    override var popularity: Double,
    override var voteCount: Int,
    override var video: Boolean,
    override var posterPath: String?,
    override var adult: Boolean,
    override var backdropPath: String?,
    override var originalLanguage: String?,
    override var originalTitle: String?,
    override var genreIds: RealmList<Int>,
    override var title: String,
    override var voteAverage: Double,
    override var overview: String?,
    override var releaseDate: String?,
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
        DbInTheatersMovie(
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
        fun fromMovie(movie: Movie) = DbInTheatersMovie().fromMovie(movie)
    }
}