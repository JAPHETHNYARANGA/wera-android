package com.werrah.wera.data.room.Database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.werrah.wera.data.room.Converters.Converters
import com.werrah.wera.data.room.Dao.ListingsDao
import com.werrah.wera.domain.models.Listings


@Database(entities = [Listings::class], version = 1, exportSchema = false)
@TypeConverters(Converters::class)
abstract class WeraDatabase :RoomDatabase() {
    abstract fun listingsDao():ListingsDao
}