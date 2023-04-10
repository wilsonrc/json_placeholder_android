package com.example.jsonapp.di

import com.example.jsonapp.data.sources.local.CommentsDao
import com.example.jsonapp.data.sources.local.JPHDatabase
import com.example.jsonapp.data.sources.local.PostsDao
import com.example.jsonapp.data.sources.local.UsersDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class DaosModule {

        @Provides
        fun providePostsDao(db: JPHDatabase): PostsDao {
            return db.postsDao()
        }

        @Provides
        fun provideCommentsDao(db: JPHDatabase): CommentsDao {
            return db.commentsDao()
        }

        @Provides
        fun provideUsersDao(db: JPHDatabase): UsersDao {
            return db.usersDao()
        }
}