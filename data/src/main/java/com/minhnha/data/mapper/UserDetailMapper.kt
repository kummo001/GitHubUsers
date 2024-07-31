package com.minhnha.data.mapper

import com.minhnha.data.model.UserDetailResponse
import com.minhnha.domain.model.User
import com.minhnha.domain.model.UserDetail

fun UserDetailResponse.toUserDetail() = UserDetail(
    loginName = loginName,
    avatarUrl = avatarUrl,
    htmlUrl = htmlUrl,
    location = location,
    followers = followers,
    following = following
)

fun UserDetail.toUser() = User(
    avatarUrl = avatarUrl,
    loginName = loginName,
    htmlUrl = htmlUrl
)