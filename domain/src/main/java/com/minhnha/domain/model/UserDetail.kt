package com.minhnha.domain.model

data class UserDetail(
    val loginName: String,
    val avatarUrl: String,
    val htmlUrl: String,
    val location: String,
    val followers: Int,
    val following: Int
)
