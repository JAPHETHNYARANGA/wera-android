package com.werrah.wera.domain.models

data class ListingsData(
    val success : Boolean,
    val message : String,
    val listings : List<Listings>
)

data class Listings(
    val id : Int?,
    val user_id : Int?,
    val name : String?,
    val description : String?,
    val Location : String?,
    val sublocation : String?,
    val category : String?,
    val amount : String?,
    val status: Int?,
    val image : String?,
    val request_count : Int?,
    val user : UserData,

)

data class UserData(
    val id: Int,
    val phone: String
)
