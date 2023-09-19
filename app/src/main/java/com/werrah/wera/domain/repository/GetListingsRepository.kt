package com.werrah.wera.domain.repository

import com.werrah.wera.data.network.GetPosts
import com.werrah.wera.domain.models.ListingsData
import javax.inject.Inject

class GetListingsRepository @Inject constructor(private val getPosts: GetPosts) {
    suspend fun getPosts() : ListingsData{
        return getPosts.getListings()
    }
}
