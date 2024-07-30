package com.minhnha.domain.usecase.remote

import com.minhnha.domain.interfaces.UserRepository
import com.minhnha.domain.model.User
import com.minhnha.domain.usecase.BaseUseCase
import javax.inject.Inject

class GetUserUseCase @Inject constructor(
    private val userRepository: UserRepository
) : BaseUseCase<Int, Result<List<User>>>() {
    override suspend fun execute(parameters: Int): Result<List<User>> {
        return userRepository.getUsers(parameters)
    }
}