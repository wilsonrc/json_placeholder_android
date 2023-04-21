package com.example.jsonapp

import com.example.jsonapp.data.sources.IJPHRepository
import com.example.jsonapp.data.sources.models.Comment
import com.example.jsonapp.data.sources.models.Post
import com.example.jsonapp.data.sources.models.User
import com.example.jsonapp.ui.HomeScreenViewModel
import com.example.jsonapp.ui.HomeUiState
import com.example.jsonapp.ui.PostDetailsUiState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.*
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito
import org.mockito.Mockito.`when`

@ExperimentalCoroutinesApi
class HomeScreenViewModelTest {

    private val testDispatcher = UnconfinedTestDispatcher()
    private lateinit var mockRepository: IJPHRepository
    private lateinit var viewModel: HomeScreenViewModel
    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        mockRepository = Mockito.mock(IJPHRepository::class.java)
        viewModel = HomeScreenViewModel(mockRepository)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }
    @Test
    fun `test loadPosts`() = runTest {
        val posts = listOf(
            Post(1, 1, "Title 1", "Body 1", false),
            Post(1, 2, "Title 2", "Body 2", true)
        )

        `when`(mockRepository.fetchRemoteUsers()).thenReturn(Unit)
        `when`(mockRepository.getPost()).thenReturn(flowOf(Result.success(posts)))

        viewModel.loadPosts()

        assertEquals(
            HomeUiState.Success(posts),
            viewModel.homeScreenUiState.value
        )
    }

    @Test
    fun `test loadPostDetail`() = runTest {
        val post = Post(1, 1, "Title 1", "Body 1", false)
        val user =
            User(1, "User 1", "user1@example.com", "www.example.com", "1234567890", "1234567890")
        val comments = listOf(Comment(1, 1, "Comment 1", "comment1@example.com", "Comment body 1"))

        `when`(mockRepository.getPost("1")).thenReturn(Result.success(post))
        `when`(mockRepository.getUser("1")).thenReturn(user)
        `when`(mockRepository.getComments("1")).thenReturn(comments)

        viewModel.loadCurrentPostDetail(post.id.toString())

        assertEquals(
            PostDetailsUiState.Success(post, user, comments),
            viewModel.postDetailsUiState.value
        )
    }

    @Test
    fun `test onFavoriteClicked`() = runTest {
        val post = Post(1, 1, "Title 1", "Body 1", false)

        viewModel.onFavoriteClicked(post)

        Mockito.verify(mockRepository).updateFavoriteState("1", !post.isFavorite)
    }
}