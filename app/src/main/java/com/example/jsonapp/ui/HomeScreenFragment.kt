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
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.room.Room
import com.example.jsonapp.R
import com.example.jsonapp.data.sources.local.PostsDatabase
import com.example.jsonapp.data.sources.remote.JPHService
import com.example.jsonapp.databinding.HomeScreenBinding
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.internal.Contexts
import kotlinx.coroutines.launch

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class HomeScreenFragment : Fragment() {

    private var _binding: HomeScreenBinding? = null
    private val binding get() = _binding!!
    private lateinit var homeScreenViewModel: HomeScreenViewModel
    private lateinit var postAdapter: PostAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = HomeScreenBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        homeScreenViewModel = ViewModelProvider(requireActivity())[HomeScreenViewModel::class.java]
        val apiService = JPHService.create()

        val database = Room.databaseBuilder(
            activity?.applicationContext!!,
            PostsDatabase::class.java,
            PostsDatabase.DATABASE_NAME
        ).build()
        homeScreenViewModel.setupViewModel(database, apiService)

        postAdapter = PostAdapter(emptyList()) {}

        binding.postsRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.postsRecyclerView.adapter = postAdapter

        observeHomeUiState()
    }

    private fun observeHomeUiState() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                homeScreenViewModel.homeScreenUiState.collect { uiState ->
                    when (uiState) {
                        is HomeScreenViewModel.HomeUiState.Loading -> {
                            // Show a loading indicator if needed
                        }
                        is HomeScreenViewModel.HomeUiState.Success -> {
                            postAdapter = PostAdapter(uiState.posts) { post ->
                                //SEND POST IN A BUNDLE
                                val bundle = Bundle().apply {
                                    putSerializable("post", post)
                                }
                                findNavController().navigate(R.id.action_HomeScreenFragment_to_PostDetailsFragment, bundle)
                            }
                            binding.postsRecyclerView.adapter = postAdapter
                        }
                        is HomeScreenViewModel.HomeUiState.Error -> {
                            // Show an error message or a retry button
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