package com.example.jsonapp.data.sources

import com.example.jsonapp.common.INetworkConnectivityChecker
import com.example.jsonapp.data.sources.local.JPHLocalDataSource
import com.example.jsonapp.data.sources.models.Comment
import com.example.jsonapp.data.sources.models.Post
import com.example.jsonapp.data.sources.models.User
import com.example.jsonapp.data.sources.remote.JPHRemoteDataSource
import com.example.jsonapp.di.IODispatcher
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.transform
import kotlinx.coroutines.withContext
import javax.inject.Inject

class JPHRepository @Inject constructor(
    private val localDataSource: JPHLocalDataSource,
    private val remoteDataSource: JPHRemoteDataSource,
    private val networkConnectivityChecker: INetworkConnectivityChecker,
    @IODispatcher private val dispatcher: CoroutineDispatcher
) : IJPHRepository {
    override suspend fun getPost(): Flow<Result<List<Post>>> {
        return withContext(dispatcher) {
            localDataSource.getPosts()
                .transform { posts ->
                    if (posts.isEmpty()) {
                        if (networkConnectivityChecker.isConnected()) {
                            fetchRemotePosts()
                            // Emit the new list of posts from the local data source after fetching from remote
                            Result.success(localDataSource.getPosts())
                        } else {
                            emit(Result.failure(Exception("No internet connection")))
                        }
                    } else {
                        emit(Result.success(posts))
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
            val users = localDataSource.getUsers()
            if (users.isEmpty()) {
                if (networkConnectivityChecker.isConnected()) {
                    val remoteUsers = remoteDataSource.getUsers()
                    localDataSource.saveUsers(remoteUsers)
                }
            }
        }
    }

    override suspend fun getPost(id: String): Result<Post?> {
        return withContext(dispatcher) {
            if (networkConnectivityChecker.isConnected()) {
                val remotePost = remoteDataSource.getPost(id) ?: return@withContext Result.failure(
                    Exception("No post found")
                )

                localDataSource.savePost(remotePost)
                Result.success(localDataSource.getPost(id))
            } else {
                Result.failure(Exception("No internet connection"))
            }
            Result.success(localDataSource.getPost(id))
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
                if (networkConnectivityChecker.isConnected()) {
                    fetchRemoteComments(postId)
                }
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