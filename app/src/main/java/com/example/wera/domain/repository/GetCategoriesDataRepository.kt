package com.example.wera.domain.repository

import com.example.wera.data.network.GetCategories
import com.example.wera.domain.models.Categories
import javax.inject.Inject


class GetCategoriesDataRepository @Inject constructor(private val getCategories: GetCategories) {
    suspend fun getCategories() : Categories{
        return getCategories.getCategories()
    }
}


