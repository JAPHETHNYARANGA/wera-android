package com.example.wera.domain.useCase

import android.util.Log
import com.example.wera.domain.models.RegisterResponse
import com.example.wera.domain.repository.RegisterUserRepository
import java.lang.Exception
import javax.inject.Inject

class RegisterUserUseCase @Inject constructor(private val registerUserRepository: RegisterUserRepository){
    suspend fun registerUser(
        name : String,
        email: String,
        password : String
    ): RegisterResponse{
        try {
            return registerUserRepository.registerUser(name, email, password)
        }catch (e:Exception){
            Log.d("useCase Error", "${e.message}")
            throw e
        }
    }
}