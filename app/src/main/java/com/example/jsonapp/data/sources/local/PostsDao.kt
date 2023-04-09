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

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun savePosts(posts: List<PostDbModel>)

    @Query("DELETE FROM posts WHERE postId = :id")
    fun deletePost(id: String)

    @Query("UPDATE posts SET favorite = :favorite WHERE postId = :postId")
    fun updateFavoriteState(postId: String, favorite: Boolean)

    @Query("SELECT * FROM posts WHERE favorite = 1")
    fun getFavoritePosts(): Flow<List<PostDbModel>>

    @Query("DELETE FROM posts WHERE favorite = 0")
    fun deleteAllNotFavoritePosts()
}