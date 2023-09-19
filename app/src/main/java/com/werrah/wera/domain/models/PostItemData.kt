package com.werrah.wera.domain.models

data class PostItemData(
    val name: String,
    val description : String,
    val location : String,
    val amount : String,
    val category : Int,
    val status : Int,
    val image :String
)

data class PostItemResponse(
    val success : Boolean,
    val message : String
)