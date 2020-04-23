package br.com.devroid.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import br.com.devroid.data.converter.*
import br.com.devroid.data.dao.*
import br.com.devroid.data.entity.local.*
import br.com.devroid.domain.entity.Genre
import br.com.devroid.domain.entity.MovieDetails

@Database(
    entities = [
        DbInTheatersMovie::class,
        DbTopRatedMovie::class,
        DbPopularMovie::class,
        DbLastPageLoadedFromNetwork::class,
        DbUser::class,
        Genre::class,
        MovieDetailsGenreCrossRef::class,
        MovieDetails::class,
        DbMovieSaved::class
    ], version = 1
)
@TypeConverters(
    DateConverter::class,
    IntConverter::class,
    DbTopRatedMovieConverter::class,
    GenreConverter::class,
    DbPopularMovieConverter::class,
    DbInTheatersMovieConverter::class
)
abstract class MovieAppDatabase : RoomDatabase() {

    abstract fun pageLoadedDao(): PageLoadedDao
    abstract fun popularMoviesDao(): PopularMoviesDao
    abstract fun topRatedMoviesDao(): TopRatedMoviesDao
    abstract fun inTheatersMoviesDao(): InTheatersMoviesDao
    abstract fun userDao(): UserDao
    abstract fun moviesDao(): MoviesDao
    abstract fun movieDetailsDao(): MovieDetailsDao
    abstract fun genreDao(): GenreDao

    companion object {
        private const val DATABASE_NAME = "movie-app"

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