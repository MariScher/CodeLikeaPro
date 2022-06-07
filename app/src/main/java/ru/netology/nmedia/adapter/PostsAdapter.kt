package ru.netology.nmedia.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import ru.netology.nmedia.R
import ru.netology.nmedia.databinding.PostBinding
import ru.netology.nmedia.dto.Post

internal class PostsAdapter(
    private val interactionListener: PostInteractionListener
) : ListAdapter<Post, PostsAdapter.ViewHolder>(DiffCallback) {

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = PostBinding.inflate(inflater, parent, false)
        return ViewHolder(binding, interactionListener)
    }

    class ViewHolder(
        private val binding: PostBinding,
        listener: PostInteractionListener
    ) : RecyclerView.ViewHolder(binding.root) {

        private lateinit var post: Post

        init {
            binding.favourite.setOnClickListener { listener.likeButtonClicked(post) }
            binding.share.setOnClickListener { listener.shareButtonClicked(post) }
            binding.play.setOnClickListener { listener.playVideoButtonClicked(post) }
            binding.videoContent.setOnClickListener { listener.playVideoButtonClicked(post) }
            binding.options.setOnClickListener { popupMenu.show() }
            binding.constraintLayout.setOnClickListener{listener.onContentClicked(post)}
        }

        private val popupMenu by lazy {
            PopupMenu(itemView.context, binding.options).apply {
                inflate(R.menu.options_post)
                setOnMenuItemClickListener { menuItem ->
                    when (menuItem.itemId) {
                        R.id.delete -> {
                            listener.deleteButtonClicked(post)
                            true
                        }
                        R.id.edit -> {
                            listener.editButtonClicked(post)
                            true
                        }
                        else -> false
                    }
                }
            }
        }

        fun bind(post: Post) {
            this.post = post

            with(binding) {
                authorName.text = post.authorName
                content.text = post.content
                date.text = post.date
                favourite.isChecked = post.likedByMe
                favourite.text = formatCount(post.likes)
                share.text = formatCount(post.shared)
                visibility.text = formatCount(post.views)
                groupVideo.visibility =
                    if (post.videoLink.isBlank()) View.GONE else View.VISIBLE
            }
        }

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