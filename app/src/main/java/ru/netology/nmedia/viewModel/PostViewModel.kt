package ru.netology.nmedia.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.netology.nmedia.adapter.PostInteractionListener
import ru.netology.nmedia.data.PostRepository
import ru.netology.nmedia.data.impl.InMemoryPostRepository
import ru.netology.nmedia.dto.Post

class PostViewModel : ViewModel(), PostInteractionListener {

    private val repository: PostRepository = InMemoryPostRepository()
    val data get() = repository.data

    val currentPost = MutableLiveData<Post?>(null)

    fun saveButtonClicked(content: String) {
        if (content.isBlank()) return

        val post = currentPost.value?.copy(
            content = content
        ) ?: Post(
            id = PostRepository.NEW_POST_ID,
            authorName = "Me",
            content = content,
            date = "today"
        )
        repository.save(post)
        currentPost.value = null
    }

    override fun likeButtonClicked(post: Post) = repository.like(post.id)

    override fun shareButtonClicked(post: Post) = repository.share(post.id)

    override fun deleteButtonClicked(post: Post) = repository.delete(post.id)

    override fun editButtonClicked(post: Post) {
        currentPost.value = post
    }

    override fun cancelButtonClicked() {
        currentPost.value = null
    }
}