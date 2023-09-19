package com.werrah.wera.domain.useCase


import android.util.Log
import com.werrah.wera.domain.models.UpdateProfileResponse
import com.werrah.wera.domain.repository.UpdateProfileRepository
import javax.inject.Inject

class UpdateProfileUseCase @Inject constructor(private val updateProfileRepository: UpdateProfileRepository) {
    suspend fun updateProfile(
        name: String, email: String, phone: String, bio: String, occupation:String, profile: String
    ) : UpdateProfileResponse{
        try {
            return  updateProfileRepository.updateProfile(name, email, phone, bio, occupation, profile)
        }catch (e:Exception){
            Log.d("useCase Error", "${e.message}")
            throw e
        }
    }
}

