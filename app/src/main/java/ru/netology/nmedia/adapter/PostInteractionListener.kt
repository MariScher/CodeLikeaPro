package ru.netology.nmedia.adapter

import ru.netology.nmedia.dto.Post

interface PostInteractionListener {

    fun likeButtonClicked(post: Post)
    fun shareButtonClicked(post: Post)
    fun deleteButtonClicked(post: Post)
    fun editButtonClicked(post: Post)
    fun playVideoButtonClicked(post: Post)
    fun cancelButtonClicked()
    fun onContentClicked(post: Post)
}