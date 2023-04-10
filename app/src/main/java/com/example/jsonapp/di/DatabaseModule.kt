package com.example.jsonapp.di

import android.content.Context
import androidx.room.Room
import com.example.jsonapp.data.sources.local.JPHDatabase
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
    fun provideJPHDatabase(
        @ApplicationContext context: Context
    ): JPHDatabase = Room.databaseBuilder(
        context,
        JPHDatabase::class.java,
        JPHDatabase.DATABASE_NAME
    ).build()
}