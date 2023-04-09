package com.example.jsonapp.data.sources.local

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "posts",
    foreignKeys = [
        ForeignKey(
            entity =
            UserDbModel::class,
            parentColumns = ["id"],
            childColumns = ["userId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [androidx.room.Index(value = ["userId"])]
)
data class PostDbModel(
    @PrimaryKey
    @ColumnInfo(name = "id")
    var postId: Int = 0,
    @ColumnInfo(name = "userId")
    var userId: Int,
    @ColumnInfo(name = "title")
    var title: String,
    @ColumnInfo(name = "body")
    var body: String,
    @ColumnInfo("isFavorite")
    var isFavorite: Boolean = false
)