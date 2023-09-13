package com.example.wera.domain.useCase

import com.example.wera.domain.models.OtherProfile
import com.example.wera.domain.repository.FetchProfileRepository
import javax.inject.Inject


class FetchProfileUseCase @Inject constructor(private val fetchProfileRepository: FetchProfileRepository) {
    suspend fun fetchProfileUseCase(userId: String) : OtherProfile{
        return fetchProfileRepository.fetchProfile(userId)
    }
}


