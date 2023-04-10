package com.example.jsonapp.ui

import com.example.jsonapp.data.sources.models.Post

sealed class HomeUiState {
    object Loading : HomeUiState()
    data class Success(val posts: List<Post>) : HomeUiState()
    data class Error(val message: String) : HomeUiState()
}