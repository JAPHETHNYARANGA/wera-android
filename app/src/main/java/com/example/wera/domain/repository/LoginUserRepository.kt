package com.example.wera.domain.repository

import com.example.wera.data.network.LoginUser
import com.example.wera.domain.models.LoginResponse
import com.example.wera.domain.models.UserLogin
import javax.inject.Inject

class LoginUserRepository @Inject constructor(private val loginUser: LoginUser) {
    suspend fun loginUser(email:String, password:String): LoginResponse{
        val loginRequest = UserLogin(email, password)
        val response = loginUser.login(loginRequest)

        if (response.isSuccessful){
            return response.body() ?: throw Exception("Response body is null")
        }else{
            throw Exception("Failed login viewmodel: ${response.raw()}")
        }
    }
}