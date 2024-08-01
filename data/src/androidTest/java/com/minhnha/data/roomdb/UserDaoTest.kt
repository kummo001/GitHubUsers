package com.minhnha.data.roomdb

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.minhnha.data.datasource.room.UserDao
import com.minhnha.data.datasource.room.UserDatabase
import dagger.hilt.android.testing.HiltAndroidRule
import org.junit.After
import org.junit.Before
import org.junit.Rule
import javax.inject.Inject
import javax.inject.Named

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
}