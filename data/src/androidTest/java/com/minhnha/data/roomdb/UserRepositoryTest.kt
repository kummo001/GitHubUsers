package com.minhnha.data.roomdb

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.filters.SmallTest
import com.google.common.truth.Truth.assertThat
import com.minhnha.data.datasource.api.ApiService
import com.minhnha.data.datasource.room.UserDao
import com.minhnha.data.datasource.room.UserDatabase
import com.minhnha.data.datasource.room.UserEntity
import com.minhnha.data.mapper.toUser
import com.minhnha.data.repo.UserRepositoryImpl
import com.minhnha.domain.interfaces.UserRepository
import com.minhnha.domain.model.UserDetail
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject
import javax.inject.Named


@SmallTest
@ExperimentalCoroutinesApi
@HiltAndroidTest
class UserRepositoryTest {
    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @Inject
    @Named("TestDatabase")
    lateinit var database: UserDatabase

    @Inject
    @Named("TestApiService")
    lateinit var apiService: ApiService

    private lateinit var dao: UserDao
    private lateinit var repository: UserRepository


    @Before
    fun setup() {
        hiltRule.inject()
        dao = database.userDao()
        repository = UserRepositoryImpl(apiService, dao)
    }

    @After
    fun tearDown() {
        database.close()
    }

    @Test
    fun `save user to db success`() = runTest {
        val user = UserEntity(id = 1, htmlUrl = "htmlUrl", avatarUrl = "avatarUrl", loginName = "mobojo")
        val listUser = listOf(user.toUser())
        repository.saveUserToDB(listUser)
        val list = dao.getUsers()
        assertThat(list).contains(user)
    }

    @Test
    fun `get users from cache success`() = runTest {
        val user = UserEntity(id = 1, htmlUrl = "htmlUrl", avatarUrl = "avatarUrl", loginName = "mobojo2")
        val listUser = listOf(user.toUser())
        repository.saveUserToDB(listUser)
        assertThat(dao.getUsers()).isNotEmpty()
        val result = repository.getUsers(1)
        result.onSuccess {
            assertThat(it).contains(user.toUser())
        }
    }

    @Test
    fun `get users from api page 1 and save to room db success`() = runTest {
        repository.getUsers(1)
        val list = dao.getUsers()
        assertThat(list).isNotEmpty()
    }

    @Test
    fun `get users from api page 2 and do not save to room db`() = runTest {
        repository.getUsers(2)
        val list = dao.getUsers()
        assertThat(list).isEmpty()
    }

    @Test
    fun `get user detail from api success`() = runTest {
        val user = UserDetail(
            loginName = "mojombo",
            following = 11,
            followers = 23975,
            location = "San Francisco",
            htmlUrl = "https://github.com/mojombo",
            avatarUrl = "https://avatars.githubusercontent.com/u/1?v=4",
        )
        var response: UserDetail? = null
        val result = repository.getUserDetail("mojombo")
        result.onSuccess {
            response = it
        }
        assertThat(response).isEqualTo(user)
    }
}