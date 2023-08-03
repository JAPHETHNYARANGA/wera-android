package com.example.wera.domain.useCase

import com.example.wera.domain.models.Listings
import com.example.wera.domain.models.ListingsData
import com.example.wera.domain.repository.GetListingsRepository
import javax.inject.Inject

class GetListingUseCase @Inject constructor(private val getListingsRepository: GetListingsRepository) {
    suspend fun getListingUseCase() : ListingsData{
        return getListingsRepository.getPosts()
    }
}