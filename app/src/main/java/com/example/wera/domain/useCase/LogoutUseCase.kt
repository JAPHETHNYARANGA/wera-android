package com.example.wera.domain.useCase

import com.example.wera.domain.models.LogoutResponse
import com.example.wera.domain.repository.LogoutRepository
import retrofit2.Response
import javax.inject.Inject

class LogoutUseCase @Inject constructor(private val logoutRepository: LogoutRepository) {
    suspend fun logoutUser() : Response<LogoutResponse> {
        return logoutRepository.logoutUser()
    }
}