package com.example.jsonapp.data.sources.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.jsonapp.data.sources.local.daos.CommentsDao
import com.example.jsonapp.data.sources.local.daos.PostsDao
import com.example.jsonapp.data.sources.local.daos.UsersDao
import com.example.jsonapp.data.sources.local.models.CommentDbModel
import com.example.jsonapp.data.sources.local.models.PostDbModel
import com.example.jsonapp.data.sources.local.models.UserDbModel

@Database(
    entities = [
        PostDbModel::class,
        CommentDbModel::class,
        UserDbModel::class
    ],
    version = 1,
    exportSchema = false
)
abstract class JPHDatabase : RoomDatabase(){
    abstract fun postsDao(): PostsDao
    abstract fun commentsDao(): CommentsDao
    abstract fun usersDao(): UsersDao
    companion object {
        const val DATABASE_NAME = "JPHDatabase.db"
    }
}