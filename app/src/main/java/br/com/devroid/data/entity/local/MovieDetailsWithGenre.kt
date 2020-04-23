package br.com.devroid.data.entity.local

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import br.com.devroid.domain.entity.Genre
import br.com.devroid.domain.entity.MovieDetails

data class MovieDetailsWithGenre(
    @Embedded val movie: MovieDetails,
    @Relation(
        parentColumn = "movieDetailsId",
        entityColumn = "genreId",
        associateBy = Junction(MovieDetailsGenreCrossRef::class)
    )
    val genres: List<Genre>
)