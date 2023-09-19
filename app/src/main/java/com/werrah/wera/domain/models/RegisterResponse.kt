package com.werrah.wera.domain.models

data class RegisterResponse(
    val success: Boolean,
    val message: String,
    val user :user
)

data class user(
    val name: String,
    val userId:String,
    val email: String,
    val id: Int
)
