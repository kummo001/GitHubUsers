package com.minhnha.domain.interfaces

import com.minhnha.domain.model.User

interface UserRepository {
    suspend fun getUsers(page: Int): Result<List<User>>
}