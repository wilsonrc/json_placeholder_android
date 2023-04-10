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
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.jsonapp.R
import com.example.jsonapp.databinding.HomeScreenBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeScreenFragment : Fragment() {

    private var _binding: HomeScreenBinding? = null
    private val binding get() = _binding!!
    private lateinit var postAdapter: PostAdapter
    private val homeScreenViewModel: HomeScreenViewModel by activityViewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = HomeScreenBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        homeScreenViewModel.loadPosts()
        postAdapter = PostAdapter(emptyList()) {}

        binding.postsRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.postsRecyclerView.adapter = postAdapter

        observeHomeUiState()
    }

    private fun observeHomeUiState() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                homeScreenViewModel.homeScreenUiState.collect { uiState ->
                    handleHomeUiStates(uiState)
                }
            }
        }
    }

    private fun handleHomeUiStates(uiState: HomeScreenViewModel.HomeUiState) {
        when (uiState) {
            is HomeScreenViewModel.HomeUiState.Loading -> {
                // Show a loading indicator if needed
            }
            is HomeScreenViewModel.HomeUiState.Success -> {
                postAdapter = PostAdapter(uiState.posts) { data ->
                    when (data) {
                        is PostAdapter.PostClickAction.PostClicked -> {
                            homeScreenViewModel.currentPost = data.post
                            val bundle = Bundle().apply {
                                putString("postId", data.post.id.toString())
                            }
                            findNavController().navigate(
                                R.id.action_HomeScreenFragment_to_PostDetailsFragment,
                                bundle
                            )
                        }
                        is PostAdapter.PostClickAction.FavoriteClicked -> {
                            homeScreenViewModel.onFavoriteClicked(data.post)
                        }
                        is PostAdapter.PostClickAction.DeleteClicked -> {
                            homeScreenViewModel.onDeleteClicked(data.post)
                        }
                    }
                }
                binding.postsRecyclerView.adapter = postAdapter
            }
            is HomeScreenViewModel.HomeUiState.Error -> {
                // Show an error message or a retry button
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}