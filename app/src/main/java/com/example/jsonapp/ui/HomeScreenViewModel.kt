package com.example.jsonapp.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.jsonapp.data.sources.JPHRepository
import com.example.jsonapp.data.sources.models.Comment
import com.example.jsonapp.data.sources.models.Post
import com.example.jsonapp.data.sources.models.User
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class HomeScreenViewModel @Inject constructor(
    val jphRepository: JPHRepository
) : ViewModel() {

    private val _homeScreenUiState = MutableStateFlow<HomeUiState>(HomeUiState.Loading)
    val homeScreenUiState: StateFlow<HomeUiState> = _homeScreenUiState

    private val _postDetailsUiState =
        MutableStateFlow<PostDetailsUiState>(PostDetailsUiState.Loading)
    val postDetailsUiState: StateFlow<PostDetailsUiState> = _postDetailsUiState

    var currentPost: Post? = null

    init {
        viewModelScope.launch(Dispatchers.IO) {
            jphRepository.fetchRemoteUsers()
            jphRepository.getPost().collect {
                _homeScreenUiState.value = HomeUiState.Success(it)
            }
        }
    }

    fun loadPostDetail(post: Post) {
        currentPost = post
        viewModelScope.launch(Dispatchers.IO) {
            post.id.let {
                _postDetailsUiState.value = PostDetailsUiState.Success(
                    post = post,
                    user = jphRepository.getUser(it.toString()),
                    comments = jphRepository.getComments(it.toString())
                )
                jphRepository.getComments(it.toString())
            }
        }
    }

    fun deleteNonFavoritePosts() = viewModelScope.launch(Dispatchers.IO) {
        jphRepository.deleteAllNonFavoritePosts()
    }

    fun onFavoriteClicked(post: Post) = viewModelScope.launch(Dispatchers.IO) {
        jphRepository.updateFavoriteState(post.id.toString(), !post.isFavorite)
    }

    fun onDeleteClicked(post: Post) {
        viewModelScope.launch(Dispatchers.IO) {
            jphRepository.deletePost(post.id.toString())
        }
    }

    sealed class PostDetailsUiState {
        object Loading : PostDetailsUiState()
        data class Success(val post: Post, val user: User, val comments: List<Comment>) :
            PostDetailsUiState()

        data class Error(val message: String) : PostDetailsUiState()
    }

    sealed class HomeUiState {
        object Loading : HomeUiState()
        data class Success(val posts: List<Post>) : HomeUiState()
        data class Error(val message: String) : HomeUiState()
    }

}