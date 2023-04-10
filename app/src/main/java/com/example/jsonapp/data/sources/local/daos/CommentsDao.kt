package com.example.jsonapp.data.sources.local.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.jsonapp.data.sources.local.models.CommentDbModel

@Dao
interface CommentsDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun saveComments(comments: List<CommentDbModel>)
    @Query("SELECT * FROM comments WHERE postId = :postId")
    fun getComments(postId: String): List<CommentDbModel>
}