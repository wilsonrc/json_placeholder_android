package com.example.jsonapp.di

import com.example.jsonapp.data.sources.IJPHRepository
import com.example.jsonapp.data.sources.JPHRepository
import com.example.jsonapp.data.sources.local.daos.CommentsDao
import com.example.jsonapp.data.sources.local.JPHLocalDataSource
import com.example.jsonapp.data.sources.local.daos.PostsDao
import com.example.jsonapp.data.sources.local.daos.UsersDao
import com.example.jsonapp.data.sources.remote.JPHRemoteDataSource
import com.example.jsonapp.data.sources.remote.service.JPHService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.CoroutineDispatcher

@Module
@InstallIn(ViewModelComponent::class)
class DataSourcesModule {

    @Provides
    @ViewModelScoped
    fun provideJPHRemoteDataSource(jphService: JPHService): JPHRemoteDataSource {
        return JPHRemoteDataSource(
            jphService = jphService
        )
    }

    @Provides
    @ViewModelScoped
    fun provideJPHLocalDataSource(
        postsDao: PostsDao,
        commentsDao: CommentsDao,
        usersDao: UsersDao
    ): JPHLocalDataSource {
        return JPHLocalDataSource(
            postsDao = postsDao,
            commentsDao = commentsDao,
            usersDao = usersDao
        )
    }

    @Provides
    @ViewModelScoped
    fun provideJPHRepository(
        remoteDataSource: JPHRemoteDataSource,
        localDataSource: JPHLocalDataSource,
        @IODispatcher dispatcher: CoroutineDispatcher
    ): IJPHRepository {
        return JPHRepository(
            remoteDataSource = remoteDataSource,
            localDataSource = localDataSource,
            dispatcher = dispatcher
        )
    }
}