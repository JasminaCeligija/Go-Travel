package com.example.gotravel.common.model

//TODO : remodel this class completely
data class Review (
    val id: Int,
    val userName: String = "",
    val content: String = "",
    val createdAt: String = "",
    val numOfLikes: Int = 0,
    val numOfComments: Int = 0,
    val imgResourceId: Int
)