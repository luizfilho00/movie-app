package br.com.devroid.data.dao

import androidx.room.*
import br.com.devroid.data.entity.local.DbUser

@Dao
interface UserDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUser(user: DbUser)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateUser(user: DbUser)

    @Query("SELECT * FROM user WHERE user.id = 0")
    suspend fun getUser(): DbUser?
}