package com.example.jsonapp.di

import com.example.jsonapp.data.sources.local.daos.CommentsDao
import com.example.jsonapp.data.sources.local.JPHDatabase
import com.example.jsonapp.data.sources.local.daos.PostsDao
import com.example.jsonapp.data.sources.local.daos.UsersDao
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