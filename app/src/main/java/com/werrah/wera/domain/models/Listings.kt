package com.werrah.wera.domain.models

import androidx.room.Entity
import androidx.room.PrimaryKey

data class ListingsData(
    val success : Boolean,
    val message : String,
    val listings : ListingsWrapper
)
data class ListingsWrapper(
    val current_page: Int,
    val data: List<Listings>,
    val first_page_url: String,
    val from: Int,
    val last_page: Int,
    val last_page_url: String,
    val links: List<PageLink>,
    val next_page_url: String?,
    val path: String,
    val per_page: Int,
    val prev_page_url: String?,
    val to: Int,
    val total: Int
)


@Entity(tableName = "listings")
data class Listings(
    @PrimaryKey
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

data class PageLink(
    val url: String?,
    val label: String,
    val active: Boolean
)
