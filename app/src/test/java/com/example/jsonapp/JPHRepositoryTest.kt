package com.example.jsonapp

import com.example.jsonapp.data.sources.IJPHRepository
import com.example.jsonapp.data.sources.local.JPHLocalDataSource
import com.example.jsonapp.data.sources.remote.JPHRemoteDataSource
import kotlinx.coroutines.flow.first
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever
import com.example.jsonapp.data.sources.JPHRepository
import com.example.jsonapp.data.sources.models.Post
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.*
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class JPHRepositoryTest {

    private val localDataSource: JPHLocalDataSource = mock()
    private val remoteDataSource: JPHRemoteDataSource = mock()
    private val dispatcher = StandardTestDispatcher()

    private lateinit var repository: IJPHRepository

    @Before
    fun setup() {
        repository = JPHRepository(localDataSource, remoteDataSource, dispatcher)
    }

    @Test
    fun `test getPost`() = runTest {
        val posts = listOf(
            Post(1, 1, "Title 1", "Body 1", false),
            Post(2, 1, "Title 2", "Body 2", false)
        )

        whenever(localDataSource.getPosts()).thenReturn(flowOf(posts))

        val result = repository.getPost().first()
        advanceUntilIdle()
        assertEquals(posts, result)
    }
}