package com.werrah.wera.domain.repository

import com.werrah.wera.data.network.RegisterUser
import com.werrah.wera.domain.models.RegisterRequest
import com.werrah.wera.domain.models.RegisterResponse
import javax.inject.Inject

class RegisterUserRepository @Inject constructor(private val registerUser: RegisterUser){
    suspend fun registerUser(name: String, email:String, password:String): RegisterResponse{
        val registerRequest = RegisterRequest(name, email, password)
        val response = registerUser.register(registerRequest)
        if (response.isSuccessful){
            return response.body() ?: throw Exception("Response body is null")
        }else{
            throw Exception("Failed Register viewmodel: ${response.raw()}")
        }
    }
}