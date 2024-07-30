package com.minhnha.data.mapper

import com.minhnha.data.datasource.room.UserEntity
import com.minhnha.data.model.UsersResponse
import com.minhnha.domain.model.User

fun UserEntity.toUser() = User(
    loginName = loginName,
    avatarUrl = avatarUrl,
    htmlUrl = htmlUrl
)

fun UsersResponse.toUser() = User(
    loginName = loginName ?: "Unknown",
    avatarUrl = avatarUrl ?: "Unknown",
    htmlUrl = htmlUrl ?: "Unknown"
)

fun User.toUserEntity() = UserEntity(
    loginName = loginName,
    avatarUrl = avatarUrl,
    htmlUrl = htmlUrl
)