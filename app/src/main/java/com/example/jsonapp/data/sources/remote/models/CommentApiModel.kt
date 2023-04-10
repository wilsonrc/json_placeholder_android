package com.example.jsonapp.data.sources.remote.models

data class CommentApiModel(
    val postId: Int,
    val id: Int,
    val name: String,
    val email: String,
    val body: String
)
