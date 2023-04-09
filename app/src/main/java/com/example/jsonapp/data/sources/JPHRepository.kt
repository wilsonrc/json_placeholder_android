package com.example.jsonapp.data.sources

import com.example.jsonapp.data.sources.local.JPHLocalDataSource
import com.example.jsonapp.data.sources.models.Post
import com.example.jsonapp.data.sources.remote.JPHRemoteDataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.onEmpty

class JPHRepository(
    private val localDataSource: JPHLocalDataSource,
    private val remoteDataSource: JPHRemoteDataSource
) {
    suspend fun getPost(): Flow<List<Post>> {
        return localDataSource.getPosts().onEmpty {
            fetchRemotePosts()
        }
    }

    private suspend fun fetchRemotePosts() {
        remoteDataSource.getPosts().collect {
            localDataSource.savePosts(it)
        }
    }

    suspend fun deletePost(id: String) {
        localDataSource.deletePost(id)
    }

    suspend fun updateFavoriteState(postId: String, favorite: Boolean) {
        localDataSource.updateFavoriteState(postId, favorite)
    }

    suspend fun deleteAllNotFavoritePosts() {
        localDataSource.deleteAllNotFavoritePosts()
    }

}