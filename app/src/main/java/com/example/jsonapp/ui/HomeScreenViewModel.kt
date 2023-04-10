package com.example.jsonapp.ui

import androidx.annotation.VisibleForTesting
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.jsonapp.data.sources.IJPHRepository
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
            _homeScreenUiState.value = HomeUiState.Success(it)
        }
    }

    fun loadCurrentPostDetail(postId: String) = viewModelScope.launch {
        if (currentPost == null) {
            val post = jphRepository.getPost(postId)
            post?.let {
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