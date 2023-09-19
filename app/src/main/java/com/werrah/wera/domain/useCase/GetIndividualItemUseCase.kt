package com.werrah.wera.domain.useCase

import com.werrah.wera.domain.models.IndividualListing
import com.werrah.wera.domain.repository.GetIndividualItemRepository
import javax.inject.Inject


class GetIndividualItemUseCase @Inject constructor(private val getIndividualItemRepository: GetIndividualItemRepository) {
    suspend fun getIndividualItemUseCase(itemId: Int) : IndividualListing{
        return getIndividualItemRepository.getIndividualListing(itemId)
    }
}

