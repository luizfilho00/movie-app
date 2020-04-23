package br.com.devroid.domain.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "DbGenre")
data class Genre(
    @PrimaryKey
    @ColumnInfo(name = "genreId")
    val id: Int,
    val name: String
) : Serializable