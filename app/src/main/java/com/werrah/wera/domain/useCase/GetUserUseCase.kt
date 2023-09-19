package com.werrah.wera.domain.useCase

import com.werrah.wera.domain.models.GetUserData
import com.werrah.wera.domain.repository.GetUserRepository
import javax.inject.Inject


class GetUserUseCase @Inject constructor(private val getUserRepository: GetUserRepository) {
    suspend fun getUserUseCase() : GetUserData{
        return getUserRepository.getUserProfile()
    }
}


