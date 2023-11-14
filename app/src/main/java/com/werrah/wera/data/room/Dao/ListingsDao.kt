package com.werrah.wera.data.room.Dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.werrah.wera.domain.models.Listings
import kotlinx.coroutines.flow.Flow


@Dao
interface ListingsDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertListings(listings: List<Listings>)

    @Query("SELECT * FROM listings")
    fun getAllListings() : Flow<List<Listings>>
}