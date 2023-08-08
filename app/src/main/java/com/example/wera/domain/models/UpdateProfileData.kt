package com.example.wera.domain.models

import okhttp3.MultipartBody

data class UpdateProfileData(
    val name: String,
    val email:String,
    val phone:String,
    val bio: String,
    val occupation: String,
    val profile: MultipartBody.Part

)

data class UpdateProfileResponse(
    val success : Boolean,
    val message : String
)
