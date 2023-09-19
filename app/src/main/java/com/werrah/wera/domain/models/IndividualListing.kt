package com.werrah.wera.domain.models

data class IndividualListing(
    val success : Boolean?,
    val message: String?,
    val listing : listingData?,
    val user : IndUserData?
)

data class listingData(
    val id: Int?,
    val user_id:Int?,
    val name : String?,
    val description : String?,
    val Location : String?,
    val category : String?,
    val amount : String?,
    val image : String?,
    val request_count : Int?,
    val created_at: String?,
    val updated_at : String?
)

data class IndUserData(
    val id: Int?,
    val userId:String?,
    val phone: String?,
    val profile : String?,
    val rating : String?,
    val bio : String?,
    val name: String?,
    val occupation : String?,
    val email: String?,
    val email_verified_at :  String?,
    val created_at : String?,
    val updated_at : String?
)