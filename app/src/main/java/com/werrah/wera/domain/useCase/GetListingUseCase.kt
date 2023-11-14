package com.werrah.wera.domain.useCase

import com.werrah.wera.domain.models.ListingsData
import com.werrah.wera.domain.repository.GetListingsRepository
import javax.inject.Inject

class GetListingUseCase @Inject constructor(private val getListingsRepository: GetListingsRepository) {
    suspend fun getListingUseCase(currentPage:Int) : ListingsData{
        return getListingsRepository.getPosts(currentPage)
    }
}