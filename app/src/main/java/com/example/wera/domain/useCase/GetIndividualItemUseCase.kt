package com.example.wera.domain.useCase

import com.example.wera.domain.models.IndividualListing
import com.example.wera.domain.repository.GetIndividualItemRepository
import javax.inject.Inject


class GetIndividualItemUseCase @Inject constructor(private val getIndividualItemRepository: GetIndividualItemRepository) {
    suspend fun getIndividualItemUseCase(itemId: Int) : IndividualListing{
        return getIndividualItemRepository.getIndividualListing(itemId)
    }
}

