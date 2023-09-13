package com.example.wera.domain.repository

import com.example.wera.data.network.FetchProfile
import com.example.wera.domain.models.OtherProfile
import javax.inject.Inject

class FetchProfileRepository @Inject constructor(private val fetchProfile: FetchProfile) {
    suspend fun fetchProfile(userId : String) : OtherProfile{
        return fetchProfile.fetchProfile(userId)
    }
}



