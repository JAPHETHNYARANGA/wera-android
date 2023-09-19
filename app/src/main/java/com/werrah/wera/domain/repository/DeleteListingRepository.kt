package com.werrah.wera.domain.repository

import com.werrah.wera.data.network.DeleteListing
import com.werrah.wera.domain.models.DeleteListingResponse
import javax.inject.Inject

class DeleteListingRepository @Inject constructor(private val deleteListing: DeleteListing) {
    suspend fun deleteIndividualListing(itemId:Int) : DeleteListingResponse{
        return deleteListing.deleteListing(itemId)
    }
}



