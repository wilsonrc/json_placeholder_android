package com.example.jsonapp.data.sources.remote

data class CommentApiModel(
    val postId: Int,
    val id: Int,
    val name: String,
    val email: String,
    val body: String
)
