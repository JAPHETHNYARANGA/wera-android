package com.werrah.wera.domain.models

data class RegisterRequest(
    val name:String,
    val email: String,
    val password: String
)
