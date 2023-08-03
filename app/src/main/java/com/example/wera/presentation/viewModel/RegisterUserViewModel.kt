package com.example.wera.presentation.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.wera.domain.models.RegisterResponse
import com.example.wera.domain.useCase.RegisterUserUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import java.net.SocketTimeoutException
import javax.inject.Inject


@HiltViewModel
class RegisterUserViewModel @Inject constructor(private val registerUserUseCase: RegisterUserUseCase) : ViewModel() {
    private val _registerUser = MutableLiveData<RegisterResponse>()

    val registerUser : LiveData<RegisterResponse> get() =  _registerUser

    private val _registerSuccess = MutableLiveData<Boolean>()

    val registerSuccess : LiveData<Boolean> get() = _registerSuccess

    suspend fun registerUser(
        name: String,
        email:String,
        password: String
    ): RegisterResponse{
        try {
            return registerUserUseCase.registerUser(name, email, password)
        }catch (e:SocketTimeoutException){
            Log.d("exception", "Timeout Exception")
            throw RegisterException("Request timed out")
        }
    }

}

class RegisterException(message: String) : Exception(message)