package com.example.wera.presentation.viewModel

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.wera.domain.models.LogoutResponse
import com.example.wera.domain.useCase.LogoutUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import retrofit2.Response
import javax.inject.Inject


@HiltViewModel
class LogoutViewModel @Inject constructor(private val logoutUseCase: LogoutUseCase): ViewModel() {
    suspend fun logoutUser(): Response<LogoutResponse> {
        return logoutUseCase.logoutUser()
    }


}