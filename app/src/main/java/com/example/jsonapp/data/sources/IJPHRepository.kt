package com.example.jsonapp.data.sources

import com.example.jsonapp.data.sources.models.Comment
import com.example.jsonapp.data.sources.models.Post
import com.example.jsonapp.data.sources.models.User
import kotlinx.coroutines.flow.Flow

interface IJPHRepository {
    suspend fun getPost(): Flow<List<Post>>
    suspend fun getUser(id: String): User
    suspend fun getComments(postId: String): List<Comment>
    suspend fun updateFavoriteState(postId: String, isFavorite: Boolean)
    suspend fun fetchRemoteUsers()
    suspend fun deleteAllNonFavoritePosts()
    suspend fun deletePost(id: String)
}