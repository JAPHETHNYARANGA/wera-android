package com.werrah.wera.domain.models

data class OtherProfile(
    val status : Boolean,
    val user : otherUser
)
data class otherUser(
    val id: Int?,
    val userId : String?,
    val phone : String?,
    val profile : String?,
    val rating : String?,
    val bio : String?,
    val name : String?,
    val occupation : String?,
    val email : String?,

)