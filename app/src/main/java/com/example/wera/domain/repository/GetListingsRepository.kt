package com.example.wera.domain.repository

import com.example.wera.data.network.GetPosts
import com.example.wera.domain.models.Listings
import com.example.wera.domain.models.ListingsData
import javax.inject.Inject

class GetListingsRepository @Inject constructor(private val getPosts: GetPosts) {
    suspend fun getPosts() : ListingsData{
        return getPosts.getListings()
    }
}
