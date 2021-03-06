package br.com.devroid.data.dao

import androidx.paging.DataSource
import androidx.room.*
import br.com.devroid.data.entity.local.DbInTheatersMovie

@Dao
interface InTheatersMoviesDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertAll(list: List<DbInTheatersMovie>)

    @Query("SELECT * FROM DbInTheatersMovie ORDER BY sequenceId")
    fun getAll(): DataSource.Factory<Int, DbInTheatersMovie>

    @Query("SELECT * FROM DbInTheatersMovie WHERE id = :id")
    suspend fun getMovie(id: Int): DbInTheatersMovie

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateMovie(movie: DbInTheatersMovie)

    @Transaction
    @Query("UPDATE DbInTheatersMovie SET saved = :status WHERE id = :id")
    suspend fun updateMovieSavedStatus(id: Int, status: Boolean)

    @Query("SELECT COUNT(id) FROM DbInTheatersMovie")
    suspend fun getCount(): Int
}