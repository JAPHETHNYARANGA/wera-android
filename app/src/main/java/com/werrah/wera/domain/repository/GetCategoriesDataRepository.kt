package com.werrah.wera.domain.repository

import com.werrah.wera.data.network.GetCategories
import com.werrah.wera.domain.models.Categories
import javax.inject.Inject


class GetCategoriesDataRepository @Inject constructor(private val getCategories: GetCategories) {
    suspend fun getCategories() : Categories{
        return getCategories.getCategories()
    }
}


