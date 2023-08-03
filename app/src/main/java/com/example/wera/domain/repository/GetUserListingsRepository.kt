package com.example.wera.domain.repository

import com.example.wera.data.network.GetUserListings
import com.example.wera.domain.models.ListingsData
import javax.inject.Inject


class GetUserListingsRepository @Inject constructor(private val getUserListings: GetUserListings) {
    suspend fun getUserListings() : ListingsData {
        return getUserListings.getUserListings()
    }
}


