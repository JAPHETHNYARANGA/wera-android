package com.werrah.wera.domain.repository

import com.werrah.wera.data.network.GetIndividualListing
import com.werrah.wera.domain.models.IndividualListing
import javax.inject.Inject


class GetIndividualItemRepository @Inject constructor(private val getIndividualListing: GetIndividualListing) {
    suspend fun getIndividualListing(itemId:Int) : IndividualListing{
        return getIndividualListing.getIndividualListings(itemId)
    }
}


