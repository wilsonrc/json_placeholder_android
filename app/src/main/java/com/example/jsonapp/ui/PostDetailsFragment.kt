package com.example.jsonapp.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.jsonapp.databinding.PostDetailBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class PostDetailsFragment : Fragment() {

    private var _binding: PostDetailBinding? = null
    private val binding get() = _binding!!
    private val homeScreenViewModel: HomeScreenViewModel by activityViewModels()
    private lateinit var commentsAdapter: CommentsAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = PostDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val postId: String? = arguments?.getString("postId")

        commentsAdapter = CommentsAdapter()
        binding.rvComments.layoutManager = LinearLayoutManager(requireContext())
        binding.rvComments.adapter = commentsAdapter

        postId?.let {
            homeScreenViewModel.loadCurrentPostDetail(postId)
        }

        observePostDetailsUiState()
    }
    private fun observePostDetailsUiState() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                homeScreenViewModel.postDetailsUiState.collect { uiState ->
                    handlePostDetailsUiState(uiState)
                }
            }
        }
    }

    private fun handlePostDetailsUiState(uiState: PostDetailsUiState) {
        when (uiState) {
            is PostDetailsUiState.Loading -> {

            }
            is PostDetailsUiState.Success -> {
                binding.tvPostTitle.text = uiState.post.title
                binding.tvPostBody.text = uiState.post.body

                binding.tvUserName.text = uiState.user.name
                binding.tvUserEmail.text = uiState.user.email
                binding.tvUserWebsite.text = uiState.user.website

                commentsAdapter.submitList(uiState.comments)
            }
            is PostDetailsUiState.Error -> {

            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}