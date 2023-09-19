package com.werrah.wera.presentation.viewModel

import androidx.lifecycle.ViewModel
import com.werrah.wera.domain.models.LogoutResponse
import com.werrah.wera.domain.useCase.LogoutUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import retrofit2.Response
import javax.inject.Inject


@HiltViewModel
class LogoutViewModel @Inject constructor(private val logoutUseCase: LogoutUseCase): ViewModel() {
    suspend fun logoutUser(): Response<LogoutResponse> {
        return logoutUseCase.logoutUser()
    }


}