package com.example.wera.domain.useCase

import com.example.wera.domain.models.LogoutResponse
import com.example.wera.domain.repository.DeleteAccountRepository
import retrofit2.Response
import javax.inject.Inject


class DeleteAccountUseCase @Inject constructor(private val deleteAccountRepository: DeleteAccountRepository) {
    suspend fun deleteAccount() : Response<LogoutResponse>{
        return deleteAccountRepository.deleteAccount()
    }
}


