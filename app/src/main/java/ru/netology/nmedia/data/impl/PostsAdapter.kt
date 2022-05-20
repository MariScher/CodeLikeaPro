package ru.netology.nmedia.data.impl

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.DrawableRes
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import ru.netology.nmedia.R
import ru.netology.nmedia.databinding.PostBinding
import ru.netology.nmedia.dto.Post

typealias onButtonClicked = (Post) -> Unit

internal class PostsAdapter(
    private val likeButtonClicked: onButtonClicked,
    private val shareButtonClicked: onButtonClicked
) : ListAdapter<Post, PostsAdapter.ViewHolder>(DiffCallback) {

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = PostBinding.inflate(inflater, parent, false)
        return ViewHolder(binding, likeButtonClicked, shareButtonClicked)
    }

    class ViewHolder(
        private val binding: PostBinding,
        likeButtonClicked: onButtonClicked,
        shareButtonClicked: onButtonClicked
    ) : RecyclerView.ViewHolder(binding.root) {

        private lateinit var post: Post

        init {
            binding.favourite.setOnClickListener { likeButtonClicked(post) }
            binding.share.setOnClickListener { shareButtonClicked(post) }
        }

        fun bind(post: Post) {
            this.post = post

            with(binding) {
                authorName.text = post.authorName
                content.text = post.content
                date.text = post.date
                countLikes.text = formatCount(post.likes)
                countShares.text = formatCount(post.shared)
                countVisibility.text = formatCount(post.views)
                favourite.setImageResource(getLikeIconResId(post.likedByMe))
            }
        }

        @DrawableRes
        private fun getLikeIconResId(liked: Boolean) =
            if (liked) R.drawable.ic_favorite_red_24dp else R.drawable.ic_favorite_b_24dp

        private fun formatCount(number: Int): String {
            return when (number) {
                in 0..999 -> "$number"
                in 1000..999_999 -> "${(kotlin.math.floor(number.toDouble() / 100) / 10)}K"
                in 1_000_000..999_999_999 -> "${(kotlin.math.floor(number.toDouble() / 100_000) / 10)}B"
                else -> "${(kotlin.math.floor(number.toDouble() / 100_000_000) / 10)}T"
            }
        }
    }

    private object DiffCallback : DiffUtil.ItemCallback<Post>() {
        override fun areItemsTheSame(oldItem: Post, newItem: Post) =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: Post, newItem: Post) =
            oldItem == newItem
    }
}