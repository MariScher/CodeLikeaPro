package ru.netology.nmedia.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import ru.netology.nmedia.adapter.PostInteractionListener
import ru.netology.nmedia.data.PostRepository
import ru.netology.nmedia.data.impl.InMemoryPostRepository
import ru.netology.nmedia.dto.Post
import ru.netology.nmedia.util.SingleLiveEvent

class SinglePostViewModel(
    application: Application
) : AndroidViewModel(application),
    PostInteractionListener {

    private val repository: PostRepository = InMemoryPostRepository
    val data get() = repository.data

    private val currentPost = MutableLiveData<Post?>(null)

    val sharePostContent = SingleLiveEvent<String>()
    val navigateToEditContentScreenEvent = SingleLiveEvent<String>()
    val playVideoLink = SingleLiveEvent<String>()
    val deletePost = SingleLiveEvent<Unit>()

    fun getPostById(postId: Long): Post? =
        repository.getById(postId)

    fun saveButtonClicked(content: String) {
        if (content.isBlank()) return

        val post = currentPost.value?.copy(
            content = content
        ) ?: Post(
            id = PostRepository.NEW_POST_ID,
            authorName = "Me",
            content = content,
            date = "today",
        )
        repository.save(post)
        currentPost.value = null
    }

    override fun shareButtonClicked(post: Post) {
        sharePostContent.value = post.content
    }

    override fun playVideoButtonClicked(post: Post) {
        playVideoLink.value = post.videoLink
    }

    override fun onContentClicked(post: Post) {
    }

    override fun likeButtonClicked(post: Post) =
        repository.like(post.id)

    override fun deleteButtonClicked(post: Post) {
        repository.delete(post.id)
        deletePost.value = Unit
    }

    override fun editButtonClicked(post: Post) {
        currentPost.value = post
        navigateToEditContentScreenEvent.value = post.content
    }

    override fun cancelButtonClicked() {
        currentPost.value = null
    }
}