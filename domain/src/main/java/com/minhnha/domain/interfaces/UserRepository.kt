package com.minhnha.domain.interfaces

import com.minhnha.domain.model.User
import com.minhnha.domain.model.UserDetail

interface UserRepository {
    suspend fun getUsers(page: Int): Result<List<User>>
    suspend fun getUserDetail(loginName: String): Result<UserDetail>
}