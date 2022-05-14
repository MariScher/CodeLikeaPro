package ru.netology.nmedia.viewModel

import androidx.lifecycle.ViewModel
import ru.netology.nmedia.data.PostRepository
import ru.netology.nmedia.data.impl.InMemoryPostRepository

class PostViewModel : ViewModel() {

    private val repository: PostRepository = InMemoryPostRepository()
    val data get() = repository.data

    fun likeButtonClicked() = repository.like()

    fun shareButtonClicked() = repository.share()
}