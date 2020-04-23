package br.com.devroid.data.dao

import androidx.room.*
import br.com.devroid.data.entity.local.MovieDetailsGenreCrossRef
import br.com.devroid.data.entity.local.MovieDetailsWithGenre
import br.com.devroid.domain.entity.MovieDetails

@Dao
interface MovieDetailsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovieWithGenre(movieDetailsWithGenre: MovieDetailsGenreCrossRef)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovie(movie: MovieDetails)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateMovie(movie: MovieDetails)

    @Query("SELECT * FROM DbMovieDetails WHERE movieDetailsId = :id")
    suspend fun getMovie(id: Int): MovieDetailsWithGenre?

    @Transaction
    @Query("UPDATE DbMovieDetails SET saved = :status WHERE movieDetailsId = :id")
    suspend fun updateMovieSavedStatus(id: Int, status: Boolean)
}