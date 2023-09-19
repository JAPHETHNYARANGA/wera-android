package com.werrah.wera.domain.useCase

import com.werrah.wera.domain.models.DeleteListingResponse
import com.werrah.wera.domain.repository.DeleteListingRepository
import javax.inject.Inject

class DeleteListingUseCase @Inject constructor(private val deleteListingRepository: DeleteListingRepository) {
    suspend fun deleteListing(itemId: Int) : DeleteListingResponse{
        return deleteListingRepository.deleteIndividualListing(itemId)
    }
}

