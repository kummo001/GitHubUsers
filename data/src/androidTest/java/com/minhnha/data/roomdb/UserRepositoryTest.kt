package com.minhnha.data.roomdb

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.filters.SmallTest
import com.minhnha.data.datasource.api.ApiService
import com.minhnha.data.datasource.room.UserDao
import com.minhnha.data.datasource.room.UserDatabase
import com.minhnha.data.repo.UserRepositoryImpl
import com.minhnha.domain.interfaces.UserRepository
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.After
import org.junit.Before
import org.junit.Rule
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
}