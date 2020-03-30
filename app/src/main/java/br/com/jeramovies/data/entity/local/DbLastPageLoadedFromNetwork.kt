package br.com.jeramovies.data.entity.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "DbLastPageLoaded")
data class DbLastPageLoadedFromNetwork(
    @PrimaryKey val id: Int = 0,
    var lastTopRatedMoviePage: Int = 0,
    var lastPopularMoviePage: Int = 0,
    var lastInTheatersMoviePage: Int = 0
)