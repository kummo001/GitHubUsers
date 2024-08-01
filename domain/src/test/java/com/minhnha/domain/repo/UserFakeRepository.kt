package com.minhnha.domain.repo

import com.minhnha.domain.interfaces.UserRepository
import com.minhnha.domain.model.User
import com.minhnha.domain.model.UserDetail

class UserFakeRepository : UserRepository {
    override suspend fun getUsers(page: Int): Result<List<User>> {
        return Result.success(emptyList())
    }

    override suspend fun getUserDetail(loginName: String): Result<UserDetail> {
        val userDetail = UserDetail(
            loginName = loginName,
            following = 100,
            followers = 100,
            location = "NY",
            htmlUrl = "htmlUrl",
            avatarUrl = "avatarUrl",
        )
        return Result.success(userDetail)
    }

    override suspend fun saveUserToDB(users: List<User>): Boolean {
        return true
    }
}