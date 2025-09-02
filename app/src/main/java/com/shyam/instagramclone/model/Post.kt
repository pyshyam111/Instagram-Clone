package com.shyam.instagramclone.model

data class Post(
    val id: Int,  // 👈 Add this
    val profileName: Int,
    val userName: String,
    val postImageList: List<Int>,
    val description: String,
    val likedBy: List<User>,
    val profile: Int
)


data class User(
    val profileName: Int,
    val userName: String,
    val storyCount: Int = 0,
    val stories: List<Int> = listOf()
)
