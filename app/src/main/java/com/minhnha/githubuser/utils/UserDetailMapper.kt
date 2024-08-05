package com.minhnha.githubuser.utils

import com.minhnha.domain.model.User
import com.minhnha.domain.model.UserDetail


fun UserDetail.toUser() = User(
    avatarUrl = avatarUrl,
    loginName = loginName,
    htmlUrl = htmlUrl
)