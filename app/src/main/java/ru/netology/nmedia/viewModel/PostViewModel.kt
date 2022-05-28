package ru.netology.nmedia.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import ru.netology.nmedia.adapter.PostInteractionListener
import ru.netology.nmedia.data.PostRepository
import ru.netology.nmedia.data.impl.FilePostRepository
import ru.netology.nmedia.dto.Post
import ru.netology.nmedia.util.SingleLiveEvent

class PostViewModel(application: Application) : AndroidViewModel(application),
    PostInteractionListener {

    private val repository: PostRepository = FilePostRepository(application)
    val data get() = repository.data

    val sharePostContent = SingleLiveEvent<String>()
    val navigateToPostContentScreenEvent = SingleLiveEvent<Unit>()
    val playPostVideo = SingleLiveEvent<String>()
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

    override fun shareButtonClicked(post: Post) {
        sharePostContent.value = post.content
    }

    override fun deleteButtonClicked(post: Post) = repository.delete(post.id)

    override fun editButtonClicked(post: Post) {
        currentPost.value = post
    }

    override fun cancelButtonClicked() {
        currentPost.value = null
    }

    override fun playVideoButtonClicked(post: Post) {
        playPostVideo.value = post.videoLink
    }

    fun addButtonClicked() {
        navigateToPostContentScreenEvent.call()
    }
}