package br.com.devroid.data.dao

import androidx.paging.DataSource
import androidx.room.*
import br.com.devroid.data.entity.local.DbTopRatedMovie

@Dao
interface TopRatedMoviesDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertAll(list: List<DbTopRatedMovie>)

    @Query("SELECT * FROM DbTopRatedMovie ORDER BY sequenceId")
    fun getAll(): DataSource.Factory<Int, DbTopRatedMovie>

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateMovie(movie: DbTopRatedMovie)

    @Transaction
    @Query("UPDATE DbTopRatedMovie SET saved = :status WHERE id = :id")
    suspend fun updateMovieSavedStatus(id: Int, status: Boolean)

    @Query("SELECT COUNT(id) FROM DbTopRatedMovie")
    suspend fun getCount(): Int
}