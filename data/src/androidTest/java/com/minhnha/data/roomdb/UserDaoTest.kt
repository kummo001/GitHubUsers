package com.minhnha.data.roomdb

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.google.common.truth.Truth.assertThat
import com.minhnha.data.datasource.room.UserDao
import com.minhnha.data.datasource.room.UserDatabase
import com.minhnha.data.datasource.room.UserEntity
import com.minhnha.data.mapper.toUser
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject
import javax.inject.Named

@HiltAndroidTest
class UserDaoTest {
    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @Inject
    @Named("TestDatabase")
    lateinit var database: UserDatabase
    private lateinit var dao: UserDao

    @Before
    fun setup() {
        hiltRule.inject()
        dao = database.userDao()
    }

    @After
    fun tearDown() {
        database.close()
    }

    @Test
    fun `insert user result success`() = runTest {
        val user = UserEntity(id = 1, htmlUrl = "htmlUrl", avatarUrl = "avatarUrl", loginName = "mobojo")
        dao.insertUser(user)
        val list = dao.getUsers()
        assertThat(list).contains(user)
    }

    @Test
    fun `get users result success`() = runTest {
        val user = UserEntity(id = 1, htmlUrl = "htmlUrl", avatarUrl = "avatarUrl", loginName = "mobojo")
        val user2 = UserEntity(id = 2, htmlUrl = "htmlUrl", avatarUrl = "avatarUrl", loginName = "mobojo")
        dao.insertUser(user)
        dao.insertUser(user2)
        val list = dao.getUsers()
        assertThat(list).contains(user)
        assertThat(list).contains(user2)
    }

    @Test
    fun `delete users result success`() = runTest {
        val user = UserEntity(id = 1, htmlUrl = "htmlUrl", avatarUrl = "avatarUrl", loginName = "mobojo")
        val user2 = UserEntity(id = 2, htmlUrl = "htmlUrl", avatarUrl = "avatarUrl", loginName = "mobojo")
        dao.insertUser(user)
        dao.insertUser(user2)
        var list = dao.getUsers()
        assertThat(list).contains(user)
        assertThat(list).contains(user2)
        dao.deleteUsers()
        list = dao.getUsers()
        assertThat(list).isEmpty()
    }
}