package br.com.devroid.data.entity.local

import androidx.room.Entity

@Entity(primaryKeys = ["movieDetailsId", "genreId"])
data class MovieDetailsGenreCrossRef(
    val movieDetailsId: Int,
    val genreId: Int
)