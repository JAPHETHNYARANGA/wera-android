package com.werrah.wera.domain.repository

import com.werrah.wera.data.network.UpdateProfile
import com.werrah.wera.domain.models.UpdateProfileData
import com.werrah.wera.domain.models.UpdateProfileResponse

import javax.inject.Inject

class UpdateProfileRepository @Inject constructor(private val updateProfile: UpdateProfile) {
    suspend fun updateProfile(name: String, email: String, phone: String, bio: String, occupation:String, profile: String) : UpdateProfileResponse{
        val updateProfileRequest = UpdateProfileData(name, email, phone, bio, occupation, profile)
        val response = updateProfile.updateProfile(updateProfileRequest)

        if (response.isSuccessful){
            return response.body() ?: throw Exception("Response body is null")
        }else{
            throw Exception("Failed login viewmodel: ${response.raw()}")
        }
    }
}


