package com.example.jsonapp.data.sources.models

data class Post(
    val userId: Int,
    val id: Int,
    val title: String,
    val body: String,
) : java.io.Serializable