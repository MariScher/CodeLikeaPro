package ru.netology.nmedia.ui

import android.widget.PopupMenu
import ru.netology.nmedia.R
import ru.netology.nmedia.adapter.PostInteractionListener
import ru.netology.nmedia.databinding.PostBinding
import ru.netology.nmedia.dto.Post

fun PostBinding.listen(post: Post, listener: PostInteractionListener) {

    val popupMenu by lazy {
        PopupMenu(root.context, options).apply {
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

    favourite.setOnClickListener { listener.likeButtonClicked(post) }
    share.setOnClickListener { listener.shareButtonClicked(post) }
    play.setOnClickListener { listener.playVideoButtonClicked(post) }
    videoContent.setOnClickListener { listener.playVideoButtonClicked(post) }
    constraintLayout.setOnClickListener { listener.onContentClicked(post) }
    options.setOnClickListener { popupMenu.show() }

}

fun PostBinding.bind(post: Post) {
    favourite.text = formatCount(post.likes)
    share.text = formatCount(post.shared)
    visibility.text = formatCount(post.views)
    content.text = post.content
    authorName.text = post.authorName
    date.text = post.date
    favourite.isChecked = post.likedByMe
    groupVideo.visibility =
        if (post.videoLink.isBlank()) android.view.View.GONE else android.view.View.VISIBLE
}

fun formatCount(number: Int): String {
    return when (number) {
        in 0..999 -> "$number"
        in 1000..999_999 -> "${(kotlin.math.floor(number.toDouble() / 100) / 10)}K"
        in 1_000_000..999_999_999 -> "${(kotlin.math.floor(number.toDouble() / 100_000) / 10)}B"
        else -> "${(kotlin.math.floor(number.toDouble() / 100_000_000) / 10)}T"
    }
}