package com.example.wera.domain.useCase

import com.example.wera.domain.models.GetUserData
import com.example.wera.domain.repository.GetUserRepository
import javax.inject.Inject


class GetUserUseCase @Inject constructor(private val getUserRepository: GetUserRepository) {
    suspend fun getUserUseCase() : GetUserData{
        return getUserRepository.getUserProfile()
    }
}


