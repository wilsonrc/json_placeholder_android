package com.example.jsonapp.ui

import androidx.annotation.VisibleForTesting
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.jsonapp.data.sources.IJPHRepository
import com.example.jsonapp.data.sources.models.Post
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class HomeScreenViewModel @Inject constructor(
    private val jphRepository: IJPHRepository
) : ViewModel() {

    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    val _homeScreenUiState = MutableStateFlow<HomeUiState>(HomeUiState.Loading)
    val homeScreenUiState: StateFlow<HomeUiState> = _homeScreenUiState

    private val _postDetailsUiState =
        MutableStateFlow<PostDetailsUiState>(PostDetailsUiState.Loading)
    val postDetailsUiState: StateFlow<PostDetailsUiState> = _postDetailsUiState

    var currentPost: Post? = null

    fun loadPosts() = viewModelScope.launch {
        jphRepository.fetchRemoteUsers()
        jphRepository.getPost().collect {
            if(it.isSuccess){
                 it.getOrNull()?.let { i ->
                    _homeScreenUiState.value = HomeUiState.Success(i)
                 }
            }else{
                val errorMessage = it.exceptionOrNull()?.message ?: "Error loading posts"
                _homeScreenUiState.value = HomeUiState.Error(errorMessage)
            }
        }
    }

    fun loadCurrentPostDetail(postId: String) = viewModelScope.launch {
        if (currentPost == null) {
            val localPost = jphRepository.getPost(postId)
            if(localPost.isFailure){
                val errorMessage = localPost.exceptionOrNull()?.message ?: "Error loading post"
                _postDetailsUiState.value = PostDetailsUiState.Error(errorMessage)
                return@launch
            }else{
                localPost.getOrNull()?.let { post ->
                    currentPost = post
                    post.id.let {
                        val users = jphRepository.getUser(it.toString())
                        val comments = jphRepository.getComments(it.toString())

                        _postDetailsUiState.value = PostDetailsUiState.Success(
                            post = post,
                            user = users,
                            comments = comments
                        )
                    }
                }
            }
        } else {
            currentPost?.let { post ->
                post.id.let {

                    val users = jphRepository.getUser(it.toString())
                    val comments = jphRepository.getComments(it.toString())

                    _postDetailsUiState.value = PostDetailsUiState.Success(
                        post = post,
                        user = users,
                        comments = comments
                    )
                }
            }
        }

    }

    fun deleteNonFavoritePosts() = viewModelScope.launch {
        jphRepository.deleteAllNonFavoritePosts()
    }

    fun onFavoriteClicked(post: Post) = viewModelScope.launch {
        jphRepository.updateFavoriteState(post.id.toString(), !post.isFavorite)
    }

    fun onDeleteClicked(post: Post) = viewModelScope.launch {
        jphRepository.deletePost(post.id.toString())
    }
}