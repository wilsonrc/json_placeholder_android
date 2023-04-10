package com.example.jsonapp.data.sources.local

import com.example.jsonapp.data.sources.JPHDataSource
import com.example.jsonapp.data.sources.models.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class JPHLocalDataSource(
    private val postsDao: PostsDao,
    private val commentsDao: CommentsDao,
    private val usersDao: UsersDao
) : JPHDataSource {

    override suspend fun getPosts(): Flow<List<Post>> {
        return postsDao.getPosts().map { posts ->
            posts.map { post ->
                Post(
                    userId = post.userId,
                    id = post.postId,
                    title = post.title,
                    body = post.body,
                    isFavorite = post.isFavorite
                )
            }
        }
    }

    override suspend fun getPost(id: String): Post? {
        return postsDao.getPost(id)?.let { post ->
            Post(
                userId = post.userId,
                id = post.postId,
                title = post.title,
                body = post.body,
                isFavorite = post.isFavorite
            )
        }
    }

    override suspend fun savePosts(posts: List<Post>) {
        postsDao.savePosts(posts.map { post ->
            PostDbModel(
                userId = post.userId,
                postId = post.id,
                title = post.title,
                body = post.body,
                isFavorite = post.isFavorite
            )
        })
    }

    override suspend fun deletePost(id: String) {
        postsDao.deletePost(id)
    }

    override suspend fun updateFavoriteState(postId: String, favorite: Boolean) {
        postsDao.updateFavoriteState(postId, favorite)
    }

    override suspend fun getFavoritePosts(): Flow<List<Post>> {
        return postsDao.getFavoritePosts().map { posts ->
            posts.map { post ->
                Post(
                    userId = post.userId,
                    id = post.postId,
                    title = post.title,
                    body = post.body,
                    isFavorite = post.isFavorite
                )
            }
        }
    }

    override suspend fun deleteAllNotFavoritePosts() {
        postsDao.deleteAllNotFavoritePosts()
    }

    override suspend fun getComments(postId: String): List<Comment> {
        return commentsDao.getComments(postId).map { comment ->
            Comment(
                postId = comment.postId,
                id = comment.id,
                name = comment.name,
                email = comment.email,
                body = comment.body
            )
        }
    }

    override suspend fun saveComments(comments: List<Comment>) {
        commentsDao.saveComments(comments.map { comment ->
            CommentDbModel(
                postId = comment.postId,
                id = comment.id,
                name = comment.name,
                email = comment.email,
                body = comment.body
            )
        })
    }

    override suspend fun getUser(id: String): User {
        return usersDao.getUser(id).let { user ->
            User(
                id = user.id,
                name = user.name,
                username = user.username,
                email = user.email,
                phone = user.phone,
                website = user.website,
            )
        }
    }

    override suspend fun saveUser(user: User) {
        usersDao.saveUser(
            UserDbModel(
                id = user.id,
                name = user.name,
                username = user.username,
                email = user.email,
                phone = user.phone,
                website = user.website,
            )
        )
    }

    override suspend fun getUsers(): Flow<List<User>> {
        return usersDao.getUsers().map { users ->
            users.map { user ->
                User(
                    id = user.id,
                    name = user.name,
                    username = user.username,
                    email = user.email,
                    phone = user.phone,
                    website = user.website,
                )
            }
        }
    }

    override suspend fun saveUsers(users: List<User>) {
        usersDao.saveUsers(users.map { user ->
            UserDbModel(
                id = user.id,
                name = user.name,
                username = user.username,
                email = user.email,
                phone = user.phone,
                website = user.website,
            )
        })
    }
}
