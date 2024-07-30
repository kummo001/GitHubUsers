package com.minhnha.data.datasource.room

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "User")
data class UserEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int? = null,
    val loginName: String,
    val avatarUrl: String,
    val htmlUrl: String,
)
