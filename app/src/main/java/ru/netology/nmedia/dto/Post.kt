package ru.netology.nmedia.dto

data class Post(
    val id: Long,
    val authorName: String,
    val content: String,
    val date: String,
    var likes: Int = 999,
    var shared: Int = 99,
    var views: Int = 9999,
    val likedByMe: Boolean = false,
    val sharedBySmb: Boolean = false
)