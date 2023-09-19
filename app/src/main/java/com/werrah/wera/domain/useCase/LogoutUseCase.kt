package com.werrah.wera.domain.useCase

import com.werrah.wera.domain.models.LogoutResponse
import com.werrah.wera.domain.repository.LogoutRepository
import retrofit2.Response
import javax.inject.Inject

class LogoutUseCase @Inject constructor(private val logoutRepository: LogoutRepository) {
    suspend fun logoutUser() : Response<LogoutResponse> {
        return logoutRepository.logoutUser()
    }
}