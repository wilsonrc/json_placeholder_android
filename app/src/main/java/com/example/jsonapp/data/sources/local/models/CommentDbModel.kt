package com.example.jsonapp.data.sources.local.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(
    tableName = "comments",
    foreignKeys = [
        androidx.room.ForeignKey(
            entity = PostDbModel::class,
            parentColumns = ["id"],
            childColumns = ["postId"],
            onDelete = androidx.room.ForeignKey.CASCADE)
    ],
    indices = [androidx.room.Index(value = ["postId"])]
)
data class CommentDbModel(
    @ColumnInfo(name = "postId")
    var postId: Int,
    @PrimaryKey
    @ColumnInfo(name = "id")
    var id: Int,
    @ColumnInfo(name = "name")
    var name: String,
    @ColumnInfo(name = "email")
    var email: String,
    @ColumnInfo(name = "body")
    var body: String
)
