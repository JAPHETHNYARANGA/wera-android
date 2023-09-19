package com.werrah.wera.domain.useCase

import com.werrah.wera.domain.models.ListingsData
import com.werrah.wera.domain.repository.GetListingsRepository
import javax.inject.Inject

class GetListingUseCase @Inject constructor(private val getListingsRepository: GetListingsRepository) {
    suspend fun getListingUseCase() : ListingsData{
        return getListingsRepository.getPosts()
    }
}