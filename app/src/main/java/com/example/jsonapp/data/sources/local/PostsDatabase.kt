package com.example.jsonapp.data.sources.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [
        PostDbModel::class,
        CommentDbModel::class,
        UserDbModel::class
    ],
    version = 1,
    exportSchema = false
)
abstract class PostsDatabase : RoomDatabase(){

    abstract fun postsDao(): PostsDao
    abstract fun commentsDao(): CommentsDao
    abstract fun usersDao(): UsersDao

    companion object {
        const val DATABASE_NAME = "posts.db"
    }
}