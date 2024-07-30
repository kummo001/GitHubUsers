package com.minhnha.data.datasource.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface UserDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUser(message: UserEntity)

    @Query("DELETE FROM User")
    suspend fun deleteUsers()

    @Query("SELECT * FROM User")
    suspend fun getUsers(): List<UserEntity>
}