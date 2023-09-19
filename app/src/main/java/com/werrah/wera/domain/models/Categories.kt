package com.werrah.wera.domain.models

data class Categories(
    val success : Boolean,
    val message : String,
    val listings : List<CategoriesData>
)
data class CategoriesData(
    val id: Int?,
    val cat_id : Int?,
    val name : String?,
    val created_at: String?,
    val updated_at: String?
)
