package com.example.jsonapp.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.jsonapp.R
import com.example.jsonapp.data.sources.models.Post
import com.example.jsonapp.databinding.PostItemBinding

class PostAdapter(
    private val posts: List<Post>,
    private val onPostClickListener: (PostClickAction) -> Unit
) : RecyclerView.Adapter<PostAdapter.PostViewHolder>() {

    inner class PostViewHolder(private val binding: PostItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            binding.root.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    onPostClickListener(PostClickAction.PostClicked(post = posts[position]))
                }
            }
        }

        fun bind(post: Post) {
            binding.postTitleTextView.text = post.title
            binding.postBodyTextView.text = post.body

            binding.favoriteButton.setImageDrawable(
                if (post.isFavorite) {
                    binding.root.context.getDrawable(R.drawable.baseline_star_full)
                } else {
                    binding.root.context.getDrawable(R.drawable.baseline_star_empty)
                }
            )
            binding.favoriteButton.setOnClickListener {
                onPostClickListener(PostClickAction.FavoriteClicked(post = post))
            }
            binding.deletePostButton.setOnClickListener {
                onPostClickListener(PostClickAction.DeleteClicked(post = post))
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        val binding = PostItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PostViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return posts.size
    }

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        holder.bind(posts[position])
    }

    sealed class PostClickAction {
        data class PostClicked(val post: Post) : PostClickAction()
        data class FavoriteClicked(val post: Post) : PostClickAction()
        data class DeleteClicked(val post: Post) : PostClickAction()
    }
}