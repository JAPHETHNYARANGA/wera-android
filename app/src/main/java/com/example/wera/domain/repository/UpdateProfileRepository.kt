package com.example.wera.domain.repository

import com.example.wera.data.network.UpdateProfile
import com.example.wera.domain.models.UpdateProfileData
import com.example.wera.domain.models.UpdateProfileResponse

import javax.inject.Inject

class UpdateProfileRepository @Inject constructor(private val updateProfile: UpdateProfile) {
    suspend fun updateProfile(name: String, email: String, phone: String, bio: String, occupation:String) : UpdateProfileResponse{
        val updateProfileRequest = UpdateProfileData(name, email, phone, bio, occupation)
        val response = updateProfile.updateProfile(updateProfileRequest)

        if (response.isSuccessful){
            return response.body() ?: throw Exception("Response body is null")
        }else{
            throw Exception("Failed login viewmodel: ${response.raw()}")
        }
    }
}


