package com.example.wera.domain.models

import java.io.File

data class UpdateProfileData(
    val name: String,
    val email:String,
    val phone:String,
    val bio: String,
    val occupation : String,
//    val profile: File

)

data class UpdateProfileResponse(
    val success : Boolean,
    val message : String
)
