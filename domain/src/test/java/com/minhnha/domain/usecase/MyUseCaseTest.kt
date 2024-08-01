package com.minhnha.domain.usecase

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.minhnha.domain.interfaces.UserRepository
import com.minhnha.domain.repo.UserFakeRepository
import com.minhnha.domain.usecase.remote.GetUserDetailUseCase
import com.minhnha.domain.usecase.remote.GetUserUseCase
import org.junit.Before
import org.junit.Rule

class MyUseCaseTest {
    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var fakeRepository: UserRepository
    private lateinit var getUserUseCase: GetUserUseCase
    private lateinit var getUserDetailUseCase: GetUserDetailUseCase

    @Before
    fun setUp() {
        //Test double
        fakeRepository = UserFakeRepository()
        getUserUseCase = GetUserUseCase(fakeRepository)
        getUserDetailUseCase = GetUserDetailUseCase(fakeRepository)
    }
}