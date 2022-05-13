package ru.netology.nmedia.data.impl

import androidx.lifecycle.MutableLiveData
import ru.netology.nmedia.data.PostRepository
import ru.netology.nmedia.dto.Post

class InMemoryPostRepository : PostRepository {

    private val posts
        get() = checkNotNull(data.value) {
            "Data value should not be null"
        }

    override val data = MutableLiveData(
        List(10) { index ->
            Post(
                id = index + 1L,
                authorName = "Нетология. Университет интернет-профессий будущего",
                content = "Здесь будет текст поста № $index",
                date = "21 мая в 18:36",
                likes = 999,
                shared = 99,
                views = 9999,
                likedByMe = false,
                sharedBySmb = false
            )
        }
    )

    override fun like(postId: Long) {
        data.value = posts.map { it ->
            if (it.id != postId) it
            else it.copy(
                likedByMe = !it.likedByMe,
                likes = it.likes + if (!it.likedByMe) 1 else -1
            )
        }
    }

    override fun share(postId: Long) {
        data.value = posts.map {
            if (it.id != postId) it
            else it.copy(shared = it.shared++)
        }
    }
}