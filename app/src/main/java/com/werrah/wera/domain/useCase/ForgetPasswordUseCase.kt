package com.werrah.wera.domain.useCase


import android.util.Log
import com.werrah.wera.domain.models.ForgotPasswordResponse
import com.werrah.wera.domain.repository.ForgetPasswordRepository
import javax.inject.Inject

class ForgetPasswordUseCase @Inject constructor(private val forgetPasswordRepository: ForgetPasswordRepository) {
    suspend fun forgetPassword(
        email : String
    ): ForgotPasswordResponse{
        try{
            return forgetPasswordRepository.forgetPassword(email)

        }catch (e: Exception){
            Log.d("useCase Error", "${e.message}")
            throw e
        }
    }
}