package com.example.wera.domain.models

data class LoginResponse(
    val success : Boolean,
    val message : String,
    val token: String,
    val user : registerUser
)

data class registerUser(
    val id: Int,
    val userId:String,
    val phone : String,
    val profile :String,
    val rating : String,
    val name : String,
    val email : String,

)