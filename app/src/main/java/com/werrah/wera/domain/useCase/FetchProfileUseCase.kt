package com.werrah.wera.domain.useCase

import com.werrah.wera.domain.models.OtherProfile
import com.werrah.wera.domain.repository.FetchProfileRepository
import javax.inject.Inject


class FetchProfileUseCase @Inject constructor(private val fetchProfileRepository: FetchProfileRepository) {
    suspend fun fetchProfileUseCase(userId: String) : OtherProfile{
        return fetchProfileRepository.fetchProfile(userId)
    }
}


