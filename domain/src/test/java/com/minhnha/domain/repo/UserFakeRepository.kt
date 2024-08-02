package com.minhnha.domain.repo

import com.minhnha.domain.interfaces.UserRepository
import com.minhnha.domain.model.User
import com.minhnha.domain.model.UserDetail

class UserFakeRepository : UserRepository {
    private val listUser = mutableListOf<User>()
    override suspend fun getUsers(page: Int): Result<List<User>> {
        return Result.success(listUser)
    }

    override suspend fun getUserDetail(loginName: String): Result<UserDetail> {
        val userDetail = UserDetail(
            loginName = "mojombo",
            following = 11,
            followers = 23975,
            location = "San Francisco",
            htmlUrl = "https://github.com/mojombo",
            avatarUrl = "https://avatars.githubusercontent.com/u/1?v=4",
        )
        return Result.success(userDetail)
    }

    override suspend fun saveUserToDB(users: List<User>): Boolean {
        users.forEach {
            listUser.add(it)
        }
        return true
    }
}