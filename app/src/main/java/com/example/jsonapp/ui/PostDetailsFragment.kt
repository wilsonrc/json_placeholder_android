package com.example.jsonapp.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.room.Room
import com.example.jsonapp.data.sources.local.PostsDatabase
import com.example.jsonapp.data.sources.models.Comment
import com.example.jsonapp.data.sources.models.Post
import com.example.jsonapp.data.sources.models.User
import com.example.jsonapp.data.sources.remote.JPHService
import com.example.jsonapp.databinding.PostDetailBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class PostDetailsFragment : Fragment() {

    private var _binding: PostDetailBinding? = null
    private val binding get() = _binding!!
    private lateinit var homeScreenViewModel: HomeScreenViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = PostDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val post: Post = arguments?.getSerializable("post") as Post
        homeScreenViewModel = ViewModelProvider(requireActivity())[HomeScreenViewModel::class.java]
        val apiService = JPHService.create()

        val database = Room.databaseBuilder(
            activity?.applicationContext!!,
            PostsDatabase::class.java,
            PostsDatabase.DATABASE_NAME
        ).build()
        homeScreenViewModel.loadPostDetail(post)

        observePostDetailsUiState()

        // Update your views with post details


    }

    private fun observePostDetailsUiState() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                homeScreenViewModel.postDetailsUiState.collect { uiState ->
                    when (uiState) {
                        is HomeScreenViewModel.PostDetailsUiState.Loading -> {

                        }
                        is HomeScreenViewModel.PostDetailsUiState.Success -> {
                            binding.tvPostTitle.text =  uiState.post.title
                            binding.tvPostBody.text =  uiState.post.body

                            binding.tvUserName.text = uiState.user.name
                            binding.tvUserEmail.text = uiState.user.email
                            binding.tvUserWebsite.text = uiState.user.website

                            val commentsAdapter = CommentsAdapter(uiState.comments)
                            binding.rvComments.layoutManager =
                                LinearLayoutManager(requireContext())
                            binding.rvComments.adapter = commentsAdapter
                        }
                        is HomeScreenViewModel.PostDetailsUiState.Error -> {

                        }
                    }
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}