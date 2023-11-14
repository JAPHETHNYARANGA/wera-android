package com.werrah.wera.data.room.Converters

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.werrah.wera.domain.models.UserData

class Converters {
    @TypeConverter
    fun fromUserData(userData: UserData): String {
        return Gson().toJson(userData)
    }

    @TypeConverter
    fun toUserData(userDataString: String): UserData {
        return Gson().fromJson(userDataString, UserData::class.java)
    }
}