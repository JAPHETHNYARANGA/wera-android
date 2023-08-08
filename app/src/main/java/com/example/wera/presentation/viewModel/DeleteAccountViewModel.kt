package com.example.wera.presentation.viewModel

import androidx.lifecycle.ViewModel
import com.example.wera.domain.models.LogoutResponse
import com.example.wera.domain.useCase.DeleteAccountUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import retrofit2.Response
import javax.inject.Inject


@HiltViewModel
class DeleteAccountViewModel @Inject constructor(private val deleteAccountUseCase: DeleteAccountUseCase) : ViewModel() {
    suspend fun deleteAccount() : Response<LogoutResponse>{
        return deleteAccountUseCase.deleteAccount()
    }
}



