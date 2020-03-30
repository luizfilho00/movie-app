package br.com.jeramovies.data.dao

import androidx.room.*
import br.com.jeramovies.data.entity.local.DbLastPageLoadedFromNetwork

@Dao
interface PageLoadedDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPage(page: DbLastPageLoadedFromNetwork)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updatePage(page: DbLastPageLoadedFromNetwork)

    @Query("SELECT * FROM DbLastPageLoaded WHERE id = 0")
    suspend fun getLastPageLoaded(): DbLastPageLoadedFromNetwork?
}