package com.example.jsonapp.data.sources

import com.example.jsonapp.data.sources.models.*
import kotlinx.coroutines.flow.Flow

interface JPHDataSource {
    suspend fun getPosts(): Flow<List<Post>>

    suspend fun getPost(id: String): Post?
    suspend fun savePosts(posts: List<Post>)

    suspend fun savePost(post: Post)

    suspend fun deletePost(id: String)

    suspend fun updateFavoriteState(postId: String, favorite: Boolean)

    suspend fun getFavoritePosts(): Flow<List<Post>>

    suspend fun deleteAllNotFavoritePosts()

    suspend fun getComments(postId: String):List<Comment>

    suspend fun saveComments(comments: List<Comment>)

    suspend fun getUser(id: String): User

    suspend fun getUsers(): List<User>

    suspend fun saveUsers(users: List<User>)
    suspend fun saveUser(user: User)
}