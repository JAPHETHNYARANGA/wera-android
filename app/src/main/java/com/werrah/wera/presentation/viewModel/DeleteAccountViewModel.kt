package com.werrah.wera.presentation.viewModel

import androidx.lifecycle.ViewModel
import com.werrah.wera.domain.models.LogoutResponse
import com.werrah.wera.domain.useCase.DeleteAccountUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import retrofit2.Response
import javax.inject.Inject


@HiltViewModel
class DeleteAccountViewModel @Inject constructor(private val deleteAccountUseCase: DeleteAccountUseCase) : ViewModel() {
    suspend fun deleteAccount() : Response<LogoutResponse>{
        return deleteAccountUseCase.deleteAccount()
    }
}



