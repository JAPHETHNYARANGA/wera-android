package com.example.wera.domain.models

data class RegisterRequest(
    val name:String,
    val email: String,
    val password: String
)
