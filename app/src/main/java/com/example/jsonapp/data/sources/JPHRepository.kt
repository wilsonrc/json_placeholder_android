package com.example.jsonapp.data.sources

import com.example.jsonapp.data.sources.local.JPHLocalDataSource
import com.example.jsonapp.data.sources.models.Comment
import com.example.jsonapp.data.sources.models.Post
import com.example.jsonapp.data.sources.models.User
import com.example.jsonapp.data.sources.remote.JPHRemoteDataSource
import com.example.jsonapp.di.IODispatcher
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.transform
import kotlinx.coroutines.withContext
import javax.inject.Inject

class JPHRepository @Inject constructor(
    private val localDataSource: JPHLocalDataSource,
    private val remoteDataSource: JPHRemoteDataSource,
    @IODispatcher private val dispatcher: CoroutineDispatcher
) : IJPHRepository {
    override suspend fun getPost(): Flow<List<Post>> {
        return withContext(dispatcher) {
            localDataSource.getPosts()
                .transform { posts ->
                    if (posts.isEmpty()) {
                        fetchRemotePosts()
                        // Emit the new list of posts from the local data source after fetching from remote
                        localDataSource.getPosts().collect {
                            emit(it)
                        }
                    } else {
                        emit(posts)
                    }
                }
        }
    }

    private suspend fun fetchRemotePosts() {
        withContext(dispatcher) {
            remoteDataSource.getPosts().collect {
                localDataSource.savePosts(it)
            }
        }
    }

    override suspend fun fetchRemoteUsers() {
        withContext(dispatcher) {
            remoteDataSource.getUsers().collect {
                localDataSource.saveUsers(it)
            }
        }
    }

    override suspend fun getPost(id: String): Post? {
        return withContext(dispatcher) {
            localDataSource.getPost(id)
        }
    }
    override suspend fun getUser(id: String): User {
        return withContext(dispatcher) {
            localDataSource.getUser(id)
        }
    }

    override suspend fun getComments(postId: String): List<Comment> {
        return withContext(dispatcher) {
            val comments = localDataSource.getComments(postId)
            if (comments.isEmpty()) {
                fetchRemoteComments(postId)
            }
            localDataSource.getComments(postId)
        }
    }

    private suspend fun fetchRemoteComments(postId: String) {
        return withContext(dispatcher) {
            val comments = remoteDataSource.getComments(postId)
            localDataSource.saveComments(comments)
        }
    }

    override suspend fun deletePost(id: String) {
        withContext(dispatcher) {
            localDataSource.deletePost(id)
        }
    }

    override suspend fun updateFavoriteState(postId: String, isFavorite: Boolean) {
        withContext(dispatcher) {
            localDataSource.updateFavoriteState(postId, isFavorite)
        }
    }

    override suspend fun deleteAllNonFavoritePosts() {
        withContext(dispatcher) {
            localDataSource.deleteAllNotFavoritePosts()
        }
    }

}