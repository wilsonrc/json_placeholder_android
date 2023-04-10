package com.example.jsonapp.ui

import com.example.jsonapp.data.sources.models.Comment
import com.example.jsonapp.data.sources.models.Post
import com.example.jsonapp.data.sources.models.User

sealed class PostDetailsUiState {
    object Loading : PostDetailsUiState()
    data class Success(val post: Post, val user: User, val comments: List<Comment>) :
        PostDetailsUiState()

    data class Error(val message: String) : PostDetailsUiState()
}