package com.example.wera.domain.models

data class ForgotPassword(
    val email : String
)

data class ForgotPasswordResponse(
    val success : Boolean,
    val status : String
)