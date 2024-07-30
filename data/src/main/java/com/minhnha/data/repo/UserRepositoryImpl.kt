package com.minhnha.data.repo

import com.minhnha.data.datasource.api.ApiService
import com.minhnha.data.mapper.toUser
import com.minhnha.domain.interfaces.UserRepository
import com.minhnha.domain.model.User
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val apiService: ApiService
) : UserRepository {
    override suspend fun getUsers(page: Int): Result<List<User>> {
        return try {
            val response = apiService.getUsers(page = page)
            if (response.isNotEmpty()) {
                Result.success(response.map { it.toUser() })
            } else {
                Result.failure(Exception("No user found"))
            }

        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}