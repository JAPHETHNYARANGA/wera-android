package com.werrah.wera.presentation.viewModel


import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.werrah.wera.domain.models.ForgotPasswordResponse
import com.werrah.wera.domain.useCase.ForgetPasswordUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import java.net.SocketTimeoutException
import javax.inject.Inject


@HiltViewModel
class ForgetPasswordViewModel @Inject constructor(private val forgetPasswordUseCase: ForgetPasswordUseCase) : ViewModel() {
    private val _forgotPass = MutableLiveData<ForgotPasswordResponse>()
    val forgetPassword : LiveData<ForgotPasswordResponse> get() = _forgotPass

    private val _forgotSuccess = MutableLiveData<Boolean>()
    val forgotSuccess : LiveData<Boolean> get() = _forgotSuccess

    suspend fun forgotPassword(email : String) : ForgotPasswordResponse{
        try {
            return forgetPasswordUseCase.forgetPassword(email)

        } catch (e: SocketTimeoutException) {
            Log.d("exception", "Timeout Exception")
            throw LoginException("Request timed out")
        }
    }

}




