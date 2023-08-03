package com.example.wera.domain.useCase

import com.example.wera.domain.models.ListingsData
import com.example.wera.domain.repository.GetUserListingsRepository
import javax.inject.Inject


class GetUserListingsUseCase @Inject constructor(private val getUserListingsRepository: GetUserListingsRepository){
    suspend fun getUserListingsUseCase() : ListingsData{
        return getUserListingsRepository.getUserListings()
    }
}

