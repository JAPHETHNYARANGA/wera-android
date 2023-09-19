package com.werrah.wera.domain.useCase

import com.werrah.wera.domain.models.Categories
import com.werrah.wera.domain.repository.GetCategoriesDataRepository
import javax.inject.Inject


class GetCategoriesDataUseCase @Inject constructor(private val getCategoriesDataRepository: GetCategoriesDataRepository) {
    suspend fun getCategoriesUseCase() : Categories{
        return getCategoriesDataRepository.getCategories()
    }
}


