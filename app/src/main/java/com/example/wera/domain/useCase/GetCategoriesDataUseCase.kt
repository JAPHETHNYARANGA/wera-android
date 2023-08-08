package com.example.wera.domain.useCase

import com.example.wera.domain.models.Categories
import com.example.wera.domain.repository.GetCategoriesDataRepository
import javax.inject.Inject


class GetCategoriesDataUseCase @Inject constructor(private val getCategoriesDataRepository: GetCategoriesDataRepository) {
    suspend fun getCategoriesUseCase() : Categories{
        return getCategoriesDataRepository.getCategories()
    }
}


