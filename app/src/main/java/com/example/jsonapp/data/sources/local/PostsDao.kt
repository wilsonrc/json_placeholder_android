package com.example.jsonapp.data.sources.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface PostsDao {
    //SORT BY FAVORITE
    @Query("SELECT * FROM posts ORDER BY isFavorite DESC")
    fun getPosts(): Flow<List<PostDbModel>>

    @Query("SELECT * FROM posts WHERE id = :id")
    fun getPost(id: String): PostDbModel?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun savePosts(posts: List<PostDbModel>)

    @Query("DELETE FROM posts WHERE id = :id")
    fun deletePost(id: String)

    @Query("UPDATE posts SET isFavorite = :favorite WHERE id = :postId")
    fun updateFavoriteState(postId: String, favorite: Boolean)

    @Query("SELECT * FROM posts WHERE isFavorite = 1")
    fun getFavoritePosts(): Flow<List<PostDbModel>>

    @Query("DELETE FROM posts WHERE isFavorite = 0")
    fun deleteAllNotFavoritePosts()
}