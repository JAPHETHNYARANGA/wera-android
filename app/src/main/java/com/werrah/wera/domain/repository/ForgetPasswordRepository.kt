package com.werrah.wera.domain.repository

import com.werrah.wera.data.network.ForgetPasswordInterface
import com.werrah.wera.domain.models.ForgotPassword
import com.werrah.wera.domain.models.ForgotPasswordResponse
import javax.inject.Inject


class ForgetPasswordRepository @Inject constructor(private val forgotPasswordInterface: ForgetPasswordInterface) {
    suspend fun forgetPassword(email : String) : ForgotPasswordResponse{
        val forgotPasswordRequest = ForgotPassword(email)
        val response = forgotPasswordInterface.forgetPassword(forgotPasswordRequest)

        if(response.isSuccessful){
            return response.body() ?: throw Exception("Response body is null")
        }else{
            throw Exception("Failed Forgot viewmodel: ${response.raw()}")
        }
    }
}


