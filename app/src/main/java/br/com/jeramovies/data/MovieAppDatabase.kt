package br.com.jeramovies.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import br.com.jeramovies.data.converter.*
import br.com.jeramovies.data.dao.*
import br.com.jeramovies.data.entity.local.*

@Database(
    entities = [
        DbInTheatersMovie::class,
        DbTopRatedMovie::class,
        DbPopularMovie::class,
        DbLastPageLoadedFromNetwork::class,
        DbMovieSaved::class
    ], version = 1
)
@TypeConverters(
    DateConverter::class,
    IntConverter::class,
    DbTopRatedMovieConverter::class,
    DbPopularMovieConverter::class,
    DbInTheatersMovieConverter::class
)
abstract class MovieAppDatabase : RoomDatabase() {

    abstract fun pageLoadedDao(): PageLoadedDao
    abstract fun popularMoviesDao(): PopularMoviesDao
    abstract fun topRatedMoviesDao(): TopRatedMoviesDao
    abstract fun inTheatersMoviesDao(): InTheatersMoviesDao
    abstract fun moviesDao(): MoviesDao

    companion object {
        const val DATABASE_NAME = "movie-app"

        fun build(context: Context): MovieAppDatabase {
            return Room.databaseBuilder(
                context, MovieAppDatabase::class.java,
                DATABASE_NAME
            )
                .fallbackToDestructiveMigration()
                .build()
        }
    }
}