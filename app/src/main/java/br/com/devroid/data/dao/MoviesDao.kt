package br.com.devroid.data.dao

import androidx.room.*
import br.com.devroid.data.entity.local.DbMovieSaved

@Dao
interface MoviesDao {

    @Query("SELECT * FROM DbMovieSaved")
    suspend fun getSavedList(): List<DbMovieSaved>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveToMyList(movieSaved: DbMovieSaved)

    @Query("SELECT * FROM DbMovieSaved WHERE id = :id")
    suspend fun findById(id: Int): DbMovieSaved?

    @Delete
    suspend fun delete(movieSaved: DbMovieSaved): Int
}