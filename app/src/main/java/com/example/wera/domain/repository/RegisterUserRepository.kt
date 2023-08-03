package com.example.wera.domain.repository

import com.example.wera.data.network.LoginUser
import com.example.wera.data.network.RegisterUser
import com.example.wera.domain.models.RegisterRequest
import com.example.wera.domain.models.RegisterResponse
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