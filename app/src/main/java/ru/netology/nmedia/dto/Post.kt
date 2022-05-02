package ru.netology.nmedia.dto

data class Post(
    val id: Long,
    val authorName: String,
    val content: String,
    val date: String,
    var likes: Int,
    var shared: Int,
    var views: Int,
    var likedByMe: Boolean
)