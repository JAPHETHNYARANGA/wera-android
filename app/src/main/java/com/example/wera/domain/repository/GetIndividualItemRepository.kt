package com.example.wera.domain.repository

import com.example.wera.data.network.GetIndividualListing
import com.example.wera.domain.models.IndividualListing
import javax.inject.Inject


class GetIndividualItemRepository @Inject constructor(private val getIndividualListing: GetIndividualListing) {
    suspend fun getIndividualListing(itemId:Int) : IndividualListing{
        return getIndividualListing.getIndividualListings(itemId)
    }
}


