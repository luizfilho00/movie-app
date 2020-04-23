package br.com.devroid.data.entity.local

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import br.com.devroid.data.entity.ApiTemporaryToken

@Entity(tableName = "user")
data class DbUser(
    @PrimaryKey val id: Int = 0,
    @Embedded var token: ApiTemporaryToken
)