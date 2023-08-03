package com.example.wera.domain.models

data class GetUserData(
    val success : Boolean,
    val message: String,
    val user : UserProfile,
)

data class UserProfile(
    val id:Int?,
    val userId : String?,
    val phone: String?,
    val profile : String?,
    val rating: String?,
    val bio: String?,
    val name : String?,
    val occupation:String?,
    val email:String?,
    val email_verified_at:String?,
    val created_at: String?,
    val updated_at: String?
)
