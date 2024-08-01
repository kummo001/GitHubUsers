package com.minhnha.data.repo

import com.minhnha.data.datasource.api.ApiService
import com.minhnha.data.datasource.room.UserDao
import com.minhnha.data.mapper.toUser
import com.minhnha.data.mapper.toUserDetail
import com.minhnha.data.mapper.toUserEntity
import com.minhnha.domain.interfaces.UserRepository
import com.minhnha.domain.model.User
import com.minhnha.domain.model.UserDetail
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val apiService: ApiService,
    private val userDao: UserDao,
) : UserRepository {

    override suspend fun getUsers(page: Int): Result<List<User>> {
        val cachedUsers = userDao.getUsers()
        //Get page 1 users from room database
        if (cachedUsers.isNotEmpty() && page == 1) {
            return Result.success(cachedUsers.map { it.toUser() })
        }
        try {
            val response = apiService.getUsers(page = page)
            if (response.isNotEmpty()) {
                if (page == 1) {
                    withContext(Dispatchers.IO) {
                        saveUserToDB(response.map { it.toUser() })
                    }
                }
                return Result.success(response.map { it.toUser() })
            } else {
                return Result.failure(Exception("No user found"))
            }
        } catch (e: Exception) {
            return Result.failure(e)
        }
    }

    override suspend fun getUserDetail(loginName: String): Result<UserDetail> {
        return try {
            val response = apiService.getUserDetail(loginUsername = loginName)
            Result.success(response.toUserDetail())
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun saveUserToDB(users: List<User>): Boolean {
        return try {
            withContext(Dispatchers.IO) {
                users.forEach {
                    userDao.insertUser(it.toUserEntity())
                }
            }
            true
        } catch (e: Exception) {
            false
        }
    }
}