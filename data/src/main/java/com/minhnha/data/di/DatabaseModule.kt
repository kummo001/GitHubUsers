package com.minhnha.data.di

import android.content.Context
import androidx.room.Room
import com.minhnha.data.datasource.room.UserDao
import com.minhnha.data.datasource.room.UserDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {

    @Provides
    @Singleton
    fun provideAppDataBase(@ApplicationContext context: Context): UserDatabase {
        return Room.databaseBuilder(context, UserDatabase::class.java, "TextMessageDB").build()
    }

    @Provides
    fun provideDao(messageDatabase: UserDatabase): UserDao {
        return messageDatabase.userDao()
    }
}