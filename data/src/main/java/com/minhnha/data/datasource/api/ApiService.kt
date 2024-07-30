package com.minhnha.data.datasource.api

import com.minhnha.data.model.UserDetailResponse
import com.minhnha.data.model.UsersResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("users")
    suspend fun getUsers(
        @Query("per_page") perPage: Int? = 20,
        @Query("page") page: Int
    ): List<UsersResponse>

    @GET("users/{login_username}")
    suspend fun getUserDetail(
        @Path("login_username") loginUsername: String
    ): UserDetailResponse
}