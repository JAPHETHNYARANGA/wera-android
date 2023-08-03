package com.example.wera.domain.models

data class IndividualListing(
    val success : Boolean,
    val message: String,
    val listing : listingData
)

data class listingData(
    val id: Int?,
    val user_id:Int?,
    val name : String,
    val description : String?,
    val Location : String?,
    val category : String?,
    val amount : String?,
    val created_at: String?,
    val updated_at : String?
)
