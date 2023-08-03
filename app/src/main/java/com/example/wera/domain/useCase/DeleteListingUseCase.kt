package com.example.wera.domain.useCase

import com.example.wera.domain.models.DeleteListingResponse
import com.example.wera.domain.repository.DeleteListingRepository
import javax.inject.Inject

class DeleteListingUseCase @Inject constructor(private val deleteListingRepository: DeleteListingRepository) {
    suspend fun deleteListing(itemId: Int) : DeleteListingResponse{
        return deleteListingRepository.deleteIndividualListing(itemId)
    }
}

