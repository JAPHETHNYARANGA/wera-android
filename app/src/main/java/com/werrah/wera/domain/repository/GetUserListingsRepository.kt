package com.werrah.wera.domain.repository

import com.werrah.wera.data.network.GetUserListings
import com.werrah.wera.domain.models.ListingsData
import javax.inject.Inject


class GetUserListingsRepository @Inject constructor(private val getUserListings: GetUserListings) {
    suspend fun getUserListings() : ListingsData {
        return getUserListings.getUserListings()
    }
}


