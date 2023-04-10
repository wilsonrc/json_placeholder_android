package com.example.jsonapp.data.sources.remote

import com.example.jsonapp.data.sources.JPHDataSource
import com.example.jsonapp.data.sources.models.Comment
import com.example.jsonapp.data.sources.models.Post
import com.example.jsonapp.data.sources.models.User
import dagger.hilt.android.scopes.ActivityScoped
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

@ActivityScoped
class JPHRemoteDataSource @Inject constructor(
    val jphService: JPHService
) : JPHDataSource {
    override suspend fun getPosts() = flow {
        val posts = jphService.getPosts()
        val result = posts.map { post ->
            Post(
                userId = post.userId,
                id = post.id,
                title = post.title,
                body = post.body,
                isFavorite = false
            )
        }
        emit(result)
    }

    override suspend fun getPost(id: String): Post? {
        TODO("Not yet implemented")
    }

    override suspend fun savePosts(posts: List<Post>) {
        TODO("Not yet implemented")
    }

    override suspend fun deletePost(id: String) {
        TODO("Not yet implemented")
    }

    override suspend fun updateFavoriteState(postId: String, favorite: Boolean) {
        TODO("Not yet implemented")
    }

    override suspend fun getFavoritePosts(): Flow<List<Post>> {
        TODO("Not yet implemented")
    }

    override suspend fun deleteAllNotFavoritePosts() {
        TODO("Not yet implemented")
    }

    override suspend fun getComments(postId: String): List<Comment> {
        return jphService.getComments(postId).map {
            Comment(
                postId = it.postId,
                id = it.id,
                name = it.name,
                email = it.email,
                body = it.body
            )
        }
    }

    override suspend fun saveComments(comments: List<Comment>) {
        TODO("Not yet implemented")
    }

    override suspend fun getUser(id: String): User {
        val user = jphService.getUser(id)
        return User(
            id = user.id,
            name = user.name,
            username = user.username,
            email = user.email,
            phone = user.phone,
            website = user.website,
        )
    }

    override suspend fun getUsers(): Flow<List<User>> = flow {
        val users = jphService.getUsers().map {
            User(
                id = it.id,
                name = it.name,
                username = it.username,
                email = it.email,
                phone = it.phone,
                website = it.website,
            )
        }
        emit(users)
    }

    override suspend fun saveUsers(users: List<User>) {
        TODO("Not yet implemented")
    }

    override suspend fun saveUser(user: User) {
        TODO("Not yet implemented")
    }

}