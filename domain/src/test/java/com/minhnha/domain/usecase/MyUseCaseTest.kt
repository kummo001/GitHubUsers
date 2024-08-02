package com.minhnha.domain.usecase

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.google.common.truth.Truth.assertThat
import com.minhnha.domain.interfaces.UserRepository
import com.minhnha.domain.model.User
import com.minhnha.domain.model.UserDetail
import com.minhnha.domain.repo.UserFakeRepository
import com.minhnha.domain.usecase.remote.GetUserDetailUseCase
import com.minhnha.domain.usecase.remote.GetUserUseCase
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

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

    @Test
    fun `get users use case success`() = runTest {
        val user = User(htmlUrl = "htmlUrl", avatarUrl = "avatarUrl", loginName = "mobojo")
        val user2 = User(htmlUrl = "htmlUrl", avatarUrl = "avatarUrl", loginName = "mojombo")
        val listUser = listOf(user, user2)
        fakeRepository.saveUserToDB(listUser)
        val result = getUserUseCase.invoke(1)
        result.onSuccess {
            assertThat(it).contains(user)
            assertThat(it).contains(user2)
        }
    }

    @Test
    fun `get user detail use case success`() = runTest {
        val userDetail = UserDetail(
            loginName = "mojombo",
            following = 11,
            followers = 23975,
            location = "San Francisco",
            htmlUrl = "https://github.com/mojombo",
            avatarUrl = "https://avatars.githubusercontent.com/u/1?v=4",
        )
        val result = getUserDetailUseCase.invoke("mojombo")
        result.onSuccess {
            assertThat(it).isEqualTo(userDetail)
        }
    }
}