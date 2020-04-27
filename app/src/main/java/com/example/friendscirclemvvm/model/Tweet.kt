package com.example.friendscirclemvvm.model

data class Tweet(
    val comments: List<Comment>?,
    val content: String?,
    val images: List<Image>?,
    val sender: SenderX?
)

data class Comment(
    val content: String,
    val sender: Sender?
)

data class Image(
    val url: String
)

data class SenderX(
    val avatar: String,
    val nick: String,
    val username: String
)

data class Sender(
    val avatar: String,
    val nick: String,
    val username: String
)