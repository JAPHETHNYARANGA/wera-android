package com.werrah.wera.domain.models

data class FavoritesResponse(
    val success : Boolean?,
    val message : String?
)
data class Favorites(
    val success: Boolean?,
    val message: String?,
    val listings: List<FavoritesList>
)

data class FavoritesList(
    val id: Int?,
    val user_id: Int?,
    val listing_id:Int?,
    val created_at: String?,
    val updated_at : String?,
    val listing : FavoriteListings?
)

data class FavoriteListings(
    val id: Int?,
    val user_id : Int?,
    val name : String?,
    val description : String?,
    val Location : String?,
    val sublocation : String?,
    val category_id: Int?,
    val amount : String?,
    val status : Int?,
    val image : String?,
    val request_count : Int?,
    val created_at : String?,
    val updated_at : String?
)
