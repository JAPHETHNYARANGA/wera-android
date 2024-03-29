package com.example.wera.domain.useCase

import android.util.Log
import com.example.wera.domain.models.LoginResponse
import com.example.wera.domain.repository.LoginUserRepository
import java.lang.Exception
import javax.inject.Inject

class LoginUserUseCase @Inject constructor(private val loginUserRepository: LoginUserRepository) {
    suspend fun loginUser(
        email:String,
        password : String
    ): LoginResponse{
        try {
            return loginUserRepository.loginUser(email, password)
        }catch (e:Exception){
            Log.d("useCase Error", "${e.message}")
            throw e
        }
    }
}