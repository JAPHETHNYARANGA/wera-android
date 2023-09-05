package com.example.wera.domain.models

data class UpdateProfileData(
    val name: String,
    val email:String,
    val phone:String,
    val bio: String,
    val occupation: String,
    val profile: String

)

data class UpdateProfileResponse(
    val success : Boolean,
    val message : String
)
