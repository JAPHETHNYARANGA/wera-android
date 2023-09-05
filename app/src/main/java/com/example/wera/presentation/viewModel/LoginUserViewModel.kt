package com.example.wera.presentation.viewModel

import android.content.SharedPreferences
import android.provider.ContactsContract.CommonDataKinds.Email
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.wera.domain.models.LoginResponse
import com.example.wera.domain.useCase.LoginUserUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import java.net.SocketTimeoutException
import javax.inject.Inject
import javax.security.auth.login.LoginException

@HiltViewModel
class LoginUserViewModel @Inject constructor(private val loginUserUseCase: LoginUserUseCase) : ViewModel() {

    private val _loginUser = MutableLiveData<LoginResponse>()

    val loginUser: LiveData<LoginResponse> get() = _loginUser

    private val _loginSuccess = MutableLiveData<Boolean>()
    val loginSuccess: LiveData<Boolean> get() = _loginSuccess


    suspend fun loginUser(
        email: String,
        password :String
    ) : LoginResponse {
        try {
            return loginUserUseCase.loginUser(email, password)
        } catch (e: SocketTimeoutException) {
            Log.d("exception", "Timeout Exception")
            throw LoginException("Request timed out")
        }
    }

}
class LoginException(message: String) : Exception(message)