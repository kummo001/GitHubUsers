package com.minhnha.domain.usecase.remote

import com.minhnha.domain.interfaces.UserRepository
import com.minhnha.domain.model.UserDetail
import com.minhnha.domain.usecase.BaseUseCase
import javax.inject.Inject

class GetUserDetailUseCase @Inject constructor(
    private val userRepository: UserRepository
) : BaseUseCase<String, Result<UserDetail>>() {
    override suspend fun execute(parameters: String): Result<UserDetail> {
        return userRepository.getUserDetail(parameters)
    }
}