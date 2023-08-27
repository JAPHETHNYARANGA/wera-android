package com.example.wera.domain.models

data class PostMessage(
    val message : String,
    val senderId:String,
    val receiverId : String
)

data class PostMessageResponse(
    val success : Boolean,
    val message : String
)