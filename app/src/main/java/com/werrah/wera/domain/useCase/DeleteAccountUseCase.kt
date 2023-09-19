package com.werrah.wera.domain.useCase

import com.werrah.wera.domain.models.LogoutResponse
import com.werrah.wera.domain.repository.DeleteAccountRepository
import retrofit2.Response
import javax.inject.Inject


class DeleteAccountUseCase @Inject constructor(private val deleteAccountRepository: DeleteAccountRepository) {
    suspend fun deleteAccount() : Response<LogoutResponse>{
        return deleteAccountRepository.deleteAccount()
    }
}


