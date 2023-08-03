package com.example.wera.domain.repository

import com.example.wera.data.network.DeleteListing
import com.example.wera.domain.models.DeleteListingResponse
import javax.inject.Inject

class DeleteListingRepository @Inject constructor(private val deleteListing: DeleteListing) {
    suspend fun deleteIndividualListing(itemId:Int) : DeleteListingResponse{
        return deleteListing.deleteListing(itemId)
    }
}



