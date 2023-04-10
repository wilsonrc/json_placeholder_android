package com.example.jsonapp.data.sources

import com.example.jsonapp.data.sources.local.JPHLocalDataSource
import com.example.jsonapp.data.sources.models.Comment
import com.example.jsonapp.data.sources.models.Post
import com.example.jsonapp.data.sources.models.User
import com.example.jsonapp.data.sources.remote.JPHRemoteDataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.transform
import javax.inject.Inject

class JPHRepository @Inject constructor(
    private val localDataSource: JPHLocalDataSource,
    private val remoteDataSource: JPHRemoteDataSource
) {
    suspend fun getPost(): Flow<List<Post>> {
        return localDataSource.getPosts()
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

    private suspend fun fetchRemotePosts() {
        remoteDataSource.getPosts().collect {
            localDataSource.savePosts(it)
        }
    }

    suspend fun fetchRemoteUsers() {
        remoteDataSource.getUsers().collect {
            localDataSource.saveUsers(it)
        }
    }

    suspend fun getUser(id: String): User {
        return localDataSource.getUser(id)
    }

    suspend fun getComments(postId: String): List<Comment> {
        val comments = localDataSource.getComments(postId)
        if (comments.isEmpty()) {
            fetchRemoteComments(postId)
        }
        return localDataSource.getComments(postId)
    }

    private suspend fun fetchRemoteComments(postId: String) {
        val comments = remoteDataSource.getComments(postId)
        localDataSource.saveComments(comments)
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